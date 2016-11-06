package postApp.Controllers.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.Controllers.NavigationActivity;
import postApp.Controllers.logic.MqTTHandler.Retrievedata;


/**
 * Created by adinH on 2016-10-26.
 */
public class RemovePostit extends Fragment {
    Button del1;
    Button del2;
    Button del3;
    Button del4;
    Button del5;
    Button del6;
    Button del7;
    Button del8;
    Button del9;
    Button del10;

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.remove_post, container, false);

        System.out.println(((NavigationActivity) getActivity()).id1);

        del1 = (Button) myView.findViewById(R.id.del1);
        del2 = (Button) myView.findViewById(R.id.del2);
        del3 = (Button) myView.findViewById(R.id.del3);
        del4 = (Button) myView.findViewById(R.id.del4);
        del5 = (Button) myView.findViewById(R.id.del5);
        del6 = (Button) myView.findViewById(R.id.del6);
        del7 = (Button) myView.findViewById(R.id.del7);
        del8 = (Button) myView.findViewById(R.id.del8);
        del9 = (Button) myView.findViewById(R.id.del9);
        del10 = (Button) myView.findViewById(R.id.del10);

        if(((NavigationActivity) getActivity()).id1 != null){
            del1.setText(((NavigationActivity) getActivity()).id1);
            del1.setVisibility(View.VISIBLE);
        }
        if(((NavigationActivity) getActivity()).id2 != null){
            del2.setText(((NavigationActivity) getActivity()).id2);
            del2.setVisibility(View.VISIBLE);
        }

        if(((NavigationActivity) getActivity()).id3 != null){
            del3.setText(((NavigationActivity) getActivity()).id3);
            del3.setVisibility(View.VISIBLE);
        }

        if(((NavigationActivity) getActivity()).id4 != null){
            del4.setText(((NavigationActivity) getActivity()).id4);
            del4.setVisibility(View.VISIBLE);
        }
        if(((NavigationActivity) getActivity()).id5 != null){
            del5.setText(((NavigationActivity) getActivity()).id5);
            del5.setVisibility(View.VISIBLE);
        }
        if(((NavigationActivity) getActivity()).id6 != null){
            del6.setText(((NavigationActivity) getActivity()).id6);
            del6.setVisibility(View.VISIBLE);
        }
        if(((NavigationActivity) getActivity()).id7 != null){
            del7.setText(((NavigationActivity) getActivity()).id7);
            del7.setVisibility(View.VISIBLE);
        }
        if(((NavigationActivity) getActivity()).id8 != null){
            del8.setText(((NavigationActivity) getActivity()).id8);
            del8.setVisibility(View.VISIBLE);
        }
        if(((NavigationActivity) getActivity()).id9 != null){
            del9.setText(((NavigationActivity) getActivity()).id9);
            del9.setVisibility(View.VISIBLE);
        }
        if(((NavigationActivity) getActivity()).id10 != null){
            del10.setText(((NavigationActivity) getActivity()).id10);
            del10.setVisibility(View.VISIBLE);
        }

        del1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del1.getText().toString(), "Delete", "none").get();
                    del1.setVisibility(View.INVISIBLE);
                    ((NavigationActivity) getActivity()).id1 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del2.getText().toString(), "Delete", "none").get();
                    del2.setVisibility(View.INVISIBLE);
                    ((NavigationActivity) getActivity()).id2 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });

        del3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del3.getText().toString(), "Delete", "none").get();
                    del3.setVisibility(View.INVISIBLE);
                    ((NavigationActivity) getActivity()).id3 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del4.getText().toString(), "Delete", "none").get();
                    del4.setVisibility(View.INVISIBLE);
                    ((NavigationActivity) getActivity()).id4 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del5.getText().toString(), "Delete", "none").get();
                    del5.setVisibility(View.INVISIBLE);

                    ((NavigationActivity) getActivity()).id5 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del6.getText().toString(), "Delete", "none").get();
                    del6.setVisibility(View.INVISIBLE);

                    ((NavigationActivity) getActivity()).id6 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del7.getText().toString(), "Delete", "none").get();
                    del7.setVisibility(View.INVISIBLE);

                    ((NavigationActivity) getActivity()).id7 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del8.getText().toString(), "Delete", "none").get();
                    del8.setVisibility(View.INVISIBLE);

                    ((NavigationActivity) getActivity()).id8 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del9.getText().toString(), "Delete", "none").get();
                    del9.setVisibility(View.INVISIBLE);

                    ((NavigationActivity) getActivity()).id9 = null;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });
        del10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievedata Ret = new Retrievedata();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String S = "";
                try {
                    S = Ret.execute(topic, "postIt action", del10.getText().toString(), "Delete", "none").get();
                    del10.setVisibility(View.INVISIBLE);
                    ((NavigationActivity) getActivity()).id10 = null;
                    ((NavigationActivity) getActivity()).counter = 1;
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
            }
        });






        return myView;
    }
}
