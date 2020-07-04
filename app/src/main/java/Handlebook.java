import android.app.DatePickerDialog;
import android.os.Bundle;

import com.example.mylibrary.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Handlebook extends AppCompatActivity {

    private static final String TAG = "frag3";
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private ActionBar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notifications);

    }

}
