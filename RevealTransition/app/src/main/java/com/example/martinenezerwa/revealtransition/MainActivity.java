package com.example.martinenezerwa.revealtransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;


public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        Button revealButton;
        Boolean isRevealed = true;

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            revealButton = (Button)rootView.findViewById(R.id.button);
            final View myView = rootView.findViewById(R.id.theView);

            revealButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick( View v)
                {
                    MainActivity mainActivity = (MainActivity)getActivity();
                    isRevealed = !isRevealed;
                    if (isRevealed)
                    {
                        // get the center for the clipping circle
                        int cx = (myView.getLeft() + myView.getRight())/2;
                        int cy = (myView.getTop() + myView.getBottom())/2;

                        // get the initial radius for the clipping circle
                        int initialRadius = myView.getWidth();

                        // Create the animation
                        Animator anim = ViewAnimationUtils.createCircularReveal(myView,cx,cy,initialRadius,0);

                        // make the view invisible when the animation is done
                        anim.addListener(new AnimatorListenerAdapter()
                        {
                            @Override
                            public void onAnimationEnd(Animator animation)
                            {
                                super.onAnimationEnd(animation);
                                myView.setVisibility(View.INVISIBLE);
                            }
                        });
                        // star the animation
                        anim.start();
                    }
                    else
                    {
                        myView.setVisibility(View.VISIBLE);

                        // get the center
                        int cx = (myView.getLeft() + myView.getRight())/2;
                        int cy = (myView.getTop() + myView.getBottom())/2;

                        // get the final radius
                        int finalRadius = myView.getWidth();

                        // create and start animation
                        Animator anim = ViewAnimationUtils.createCircularReveal(myView,cx,cy,0,finalRadius);
                        anim.start();
                    }
                }
            });
            return rootView;
        }
    }
}
