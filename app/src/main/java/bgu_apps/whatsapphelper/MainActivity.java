package bgu_apps.whatsapphelper;

    import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView _emailLabel;
    private EditText _emailSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get email-subject widgets
        _emailLabel = (TextView) this.findViewById(R.id.lblSubject);
        _emailSubject = (EditText) this.findViewById(R.id.txtSubject);
    }

    public void HideEmail(View view){
        _emailLabel.setVisibility(View.GONE);
        _emailSubject.setVisibility(View.GONE);
    }

    public void ShowEmail(View view){
        _emailLabel.setVisibility(View.VISIBLE);
        _emailSubject.setVisibility(View.VISIBLE);
    }

    /**
     * Sending a message!
     *
     * @param view
     */
    public void SendMessage(View view){
        // First, let's get the widgets-
        EditText message_edittext = (EditText) this.findViewById(R.id.txtMessage);
        RadioButton menually = (RadioButton) this.findViewById(R.id.rdbMenually);
        RadioButton whatsapp = (RadioButton) this.findViewById(R.id.rdbWhatsappA);
        RadioButton email = (RadioButton) this.findViewById(R.id.rdbEmail);
        CheckBox cat = (CheckBox) this.findViewById(R.id.chkCat);

        // getting message and loading it to a new intent-
        String message = message_edittext.getText().toString();
        Intent msgIntent = new Intent();
        msgIntent.setAction(Intent.ACTION_SEND);
        msgIntent.putExtra(Intent.EXTRA_TEXT, message);
        msgIntent.setType("text/plain");

        // send via whatsapp
        if(whatsapp.isChecked()){
            msgIntent.setPackage("com.whatsapp");
        }

        // email option = get email subject.
        if(email.isChecked()){
            String subject = _emailSubject.getText().toString();
            msgIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            msgIntent.setPackage("com.google.android.gm");
        }

        // add image
        if(cat.isChecked()){
            String catPath = "android.resource://" + this.getPackageName() + "/drawable/" + R.drawable.cat;
            Uri catURI = Uri.parse(catPath);
            msgIntent.setType("image/png");

            // send one image
            msgIntent.putExtra(Intent.EXTRA_STREAM, catURI);

            // ** another option- send multiple images.
            // ArrayList<Uri> catArray = new ArrayList<Uri>();
            //    for(int i=0; i<10; i++)
            // catArray.add(catURI);
            // msgIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, catArray);
        }

        this.startActivity(msgIntent);

        // ** To override a default choice, if there is any-
        Intent alwaysChoose = Intent.createChooser(msgIntent, "Choose Messaging App:");
        this.startActivity(alwaysChoose);
    }
}
