package dhbk.android.a23propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    CheckBox mCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        final Button alphaButton = (Button) findViewById(R.id.alphaButton);
        final Button translateButton = (Button) findViewById(R.id.translateButton);
        final Button rotateButton = (Button) findViewById(R.id.rotateButton);
        final Button scaleButton = (Button) findViewById(R.id.scaleButton);
        final Button setButton = (Button) findViewById(R.id.setButton);

        // Fade the button out and back in
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(alphaButton,
                View.ALPHA, 0);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(ValueAnimator.REVERSE);

        // Move the button over to the right and then back
        ObjectAnimator translateAnimation =
                ObjectAnimator.ofFloat(translateButton, View.TRANSLATION_X, 800);
        translateAnimation.setRepeatCount(1);
        translateAnimation.setRepeatMode(ValueAnimator.REVERSE);

        // Spin the button around in a full circle
        ObjectAnimator rotateAnimation =
                ObjectAnimator.ofFloat(rotateButton, View.ROTATION, 360);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(ValueAnimator.REVERSE);

        // Scale the button in X and Y. Note the use of PropertyValuesHolder to animate
        // multiple properties on the same object in parallel.
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2);
        ObjectAnimator scaleAnimation =
                ObjectAnimator.ofPropertyValuesHolder(scaleButton, pvhX, pvhY);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(ValueAnimator.REVERSE);

        // Run the animations above in sequence
        AnimatorSet setAnimation = new AnimatorSet();
        setAnimation.play(translateAnimation).after(alphaAnimation).before(rotateAnimation);
        setAnimation.play(rotateAnimation).before(scaleAnimation);

        setupAnimation(alphaButton, alphaAnimation, R.animator.fade);
        setupAnimation(translateButton, translateAnimation, R.animator.move);
        setupAnimation(rotateButton, rotateAnimation, R.animator.spin);
        setupAnimation(scaleButton, scaleAnimation, R.animator.scale);
        setupAnimation(setButton, setAnimation, R.animator.combo);
    }

    private void setupAnimation(View view, final Animator animation, final int animationID) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If the button is checked, load the animation from the given resource
                // id instead of using the passed-in animation parameter. See the xml files
                // for the details on those animations.
                if (mCheckBox.isChecked()) {
                    Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, animationID);
                    anim.setTarget(v);
                    anim.start();
                    return;
                }
                animation.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
