package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class MissingPerson extends AppCompatActivity {
EditText fdate,tdate,fheight,theight,fage,tage;
TextView male,female,other;
SearchableSpinner body_spinner,complexion_spinner,face_spinner,nose_spinner,beard_spinner,moustaches_spinner,eyes_spinner,specs_spinner,blind_spinner,hair_spinner,length_spinner,color_spinner,habits_spinner,speech_spinner;
private String[] body;
String[] complexion;
    String[] face;
    String[] nose;
    String[] beard;
    String[] moustaches;
    String[] eyes;
    String[] specs;
    String[] blind;
    String[] hair;
    String[] length;
    String[] color;
    String[] habits;
    String[] speech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_person);
        initView();
        body=new String[]{"Fat","Very Fat","Normal/Medium","Strong","Muscular","Thin","Very Lanky(Skeletal)"};
        ArrayAdapter<String> bodyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,body);
        body_spinner.setAdapter(bodyAdapter);
       complexion=new String[]{"Fair","Very Fair","Foreigner Type-Fair","Wheatish","Foreigner Type-Wheatish","Brownish Dark","Blackish Dark","Foreigner Type-Dark","Sallow"};
        ArrayAdapter<String> complexionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,complexion);
       complexion_spinner.setAdapter(complexionAdapter);
        face=new String[]{"Normal","Long(Oval)","Round","Square","Receding Forehead","Protruding Face","Oval","Square/Heavy Jaw","Wrinkled","Others"};
        ArrayAdapter<String> faceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,face);
        face_spinner.setAdapter(faceAdapter);
        nose=new String[]{"Normal Nose","Long Nose","Hooked(Parrot) Nose","Pointed Nose","Small Nose","Deformed Nose","Broad Nostrils(Markedly Dilated)","Bulbous Nose","Snub/Pug Nose","Pierced Nose","Turned Up Nostrils","Nose Peculisr","No Nose"};
        ArrayAdapter<String> noseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,nose);
        nose_spinner.setAdapter(noseAdapter);
        beard=new String[]{"Beraded","Clean Shaven","Goatee","Imperial/Rajputi","Long-Thick","Long-Thin","Long Flowing","Rolled and Tied","Short-Thick","Short-Thin","Sideburn","Moix Beard","Makkhi Cut","Others"};
        ArrayAdapter<String> beardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,beard);
        beard_spinner.setAdapter(beardAdapter);
        moustaches=new String[]{"Clipped","Droopping","Fly Type","Half Moustache","Handle Bar","Pencil","Tooth Brush","Turned Up"};
        ArrayAdapter<String> moustachesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,moustaches);
        moustaches_spinner.setAdapter(moustachesAdapter);
        eyes=new String[]{"Normal","Sunken","Conspicuously Large","Conspicuously Small","Markedly Closely Placed","Markedly Widely Placed","Other"};
        ArrayAdapter<String> eyesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,eyes);
        eyes_spinner.setAdapter(eyesAdapter);
        specs=new String[]{"No","Yes"};
        ArrayAdapter<String> specsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,specs);
        specs_spinner.setAdapter(specsAdapter);
       blind=new String[]{"No","Blind in one eye","Blind in both eyes","Artificial left eye","Artificial right eye","Both eyes artificial"};
        ArrayAdapter<String> blindAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,blind);
        blind_spinner.setAdapter(blindAdapter);
        hair=new String[]{"Normal","Full Bald","Front Bald","Centre Bald","Curly","Other"};
        ArrayAdapter<String> hairAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,hair);
       hair_spinner.setAdapter(hairAdapter);
       length=new String[]{"Lengthy","Short","Normal"};
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,length);
        length_spinner.setAdapter(lengthAdapter);
        color=new String[]{"Black","Brown","Red","White","Other"};
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,color);
        color_spinner.setAdapter(colorAdapter);
        habits=new String[]{"Bragging","Chews Betal","Chews Gum","Chews Pan Masala","Chews Supari","Chews Tobacco","Cinema Crazy","Drug Addict","No Habit","Others"};
        ArrayAdapter<String> habitsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,habits);
        habits_spinner.setAdapter(habitsAdapter);
        speech=new String[]{"Normal","Fast","Slow","Stammering","Loud","Soft","Deep(Heavy)","Nasal"};
        ArrayAdapter<String> speechAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,speech);
       speech_spinner.setAdapter(speechAdapter);
    }
    private void initView()
    {
        fdate=findViewById(R.id.fdate);
        tdate=findViewById(R.id.tdate);
        fheight=findViewById(R.id.fheight);
        theight=findViewById(R.id.theight);
        fage=findViewById(R.id.fage);
        tage=findViewById(R.id.tage);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        other=findViewById(R.id.other);
        body_spinner=findViewById(R.id.body_spinner);
        complexion_spinner=findViewById(R.id.complexion_spinner);
        face_spinner=findViewById(R.id.face_spinner);
        nose_spinner=findViewById(R.id.nose_spinner);
        beard_spinner=findViewById(R.id.beard_spinner);
        moustaches_spinner=findViewById(R.id.moustaches_spinner);
        eyes_spinner=findViewById(R.id.eyes_spinner);
        specs_spinner=findViewById(R.id.specs_spinner);
        blind_spinner=findViewById(R.id.blind_spinner);
        hair_spinner=findViewById(R.id.hair_spinner);
        length_spinner=findViewById(R.id.length_spinner);
        color_spinner=findViewById(R.id.color_spinner);
        habits_spinner=findViewById(R.id.habits_spinner);
        speech_spinner=findViewById(R.id.speech_spinner);


    }
}
