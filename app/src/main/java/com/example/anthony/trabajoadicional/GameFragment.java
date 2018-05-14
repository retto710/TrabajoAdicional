package com.example.anthony.trabajoadicional;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.ScriptC;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anthony.trabajoadicional.BusinessLogic.IQuestionService;
import com.example.anthony.trabajoadicional.BusinessLogic.QuestionService;
import com.example.anthony.trabajoadicional.Entities.Question;
import com.example.anthony.trabajoadicional.Entities.Score;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final long START_TIME_IN_MILLIS=30000;
    private TextView mTextViewCountDown;
    private TextView textViewQuestion;
    private TextView textViewDifficulty;
    private TextView textViewCategory;
    // TODO: Rename and change types of parameters
    private CountDownTimer mCountDownTimer;
    private String valueUsername;
    private Long valueUserid;
    private int correctAnswer=0;
    private long mTimeLeftInMillis=START_TIME_IN_MILLIS;
    private int contador=0;
    private OnFragmentInteractionListener mListener;
    private  ArrayList<Question>questions;
    private Score newScore;
    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            valueUsername=getArguments().getString("username");
            valueUserid=getArguments().getLong("userid");
            Log.d("userid",valueUserid.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_game, container, false);
         mTextViewCountDown = view.findViewById(R.id.txtViewCountdown);
         textViewCategory= view.findViewById(R.id.txtCategory);
         textViewDifficulty=view.findViewById(R.id.txtDifficulty);
         textViewQuestion= view.findViewById(R.id.txtQuestion);

         final TextView textViewAnswer = view.findViewById(R.id.correctAnswer);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnNo = view.findViewById(R.id.btnNo);
        newScore = new Score();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer= questions.get(contador).getAnswer();
                if(answer.equals("True")) {
                    textViewAnswer.setTextColor(getResources().getColor(R.color.green));
                    textViewAnswer.setText("Correct");
                    newScore.correcto(questions.get(contador).getDifficulty());
                    correctAnswer++;
                }
                else{
                    textViewAnswer.setTextColor(getResources().getColor(R.color.red));
                    textViewAnswer.setText("Incorrect");
                    newScore.incorrecto(questions.get(contador).getDifficulty());
                }
                contador++;
                Log.d("Puntaje",String.valueOf(newScore.getScore()));
                if(contador<49)
                {
                    LoadQuestion();
                }
                else
                {
                    int seconds = (int) mTimeLeftInMillis/1000;
                    newScore.finish(seconds);
                    gameOver();
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer= questions.get(contador).getAnswer();
                if(answer.equals("False")) {
                    textViewAnswer.setTextColor(getResources().getColor(R.color.green));
                    textViewAnswer.setText("Correct");
                    newScore.correcto(questions.get(contador).getDifficulty());
                    correctAnswer++;
                }
                else{

                    textViewAnswer.setTextColor(getResources().getColor(R.color.red));
                    textViewAnswer.setText("Incorrect");
                    newScore.incorrecto(questions.get(contador).getDifficulty());
                }
                contador++;
                Log.d("Puntaje",String.valueOf(newScore.getScore()));
                if(contador<49)
                {
                    LoadQuestion();
                }
                else
                {
                    int seconds = (int) mTimeLeftInMillis/1000;
                    newScore.finish(seconds);
                    gameOver();
                }
            }
        });
         updateCountDownText();
        return view;
    }

    private void gameOver(){
        Fragment fragment= new ResumeFragment();
        //List<Score> listScore= Score.find(Score.class, "name=? and score = 0",valueUsername);
        Log.d("Score",String.valueOf(newScore.getScore()));
        Score myScore = Score.findById(Score.class,valueUserid);
        Date currentDate = Calendar.getInstance().getTime();
        //Score lastScore = listScore.get(0);
        int score = newScore.getScore();
        myScore.setDate(currentDate);
        myScore.setScore(score);
        myScore.save();
        Bundle bundle = new Bundle();
        bundle.putLong("userid",valueUserid);

        bundle.putInt("correctanswer",correctAnswer);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
    @Override
    public void onStart() {
        super.onStart();
        final GetTask getTask = new GetTask();
        getTask.execute();
        startTimer();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private void startTimer()
    {
        mCountDownTimer= new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis= millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {

                gameOver();
            }
        }.start();

    }

    private void updateCountDownText(){
        int minutes = (int) mTimeLeftInMillis/1000/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;
        String timeleftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        mTextViewCountDown.setText(timeleftFormatted);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void LoadQuestion(){

        Question question= questions.get(contador);
        textViewCategory.setText(question.getCategory());
        textViewQuestion.setText(question.getQuestion());
        textViewDifficulty.setText(question.getDifficulty());

    }
    class GetTask extends AsyncTask<String,ArrayList<Question>,ArrayList<Question>>
    {


        @Override
        protected ArrayList<Question> doInBackground(String... strings) {

            IQuestionService iItemCateogryService = new QuestionService();
            questions = iItemCateogryService.getQuestions();
            return questions;
        }

        @Override
        protected void onPostExecute(ArrayList<Question> questionss) {
           super.onPostExecute(questionss);
           LoadQuestion();

        }
    }
}
