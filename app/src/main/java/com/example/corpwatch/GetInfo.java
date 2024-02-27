package com.example.corpwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetInfo extends AppCompatActivity {

    View divider;
    EditText examGeneralStagesNameInput;
    EditText examGeneralStagesDateInputF;
    EditText examGeneralStagesDateInputT;
    EditText examGeneralStagesResultInput;

    HashMap<String, byte[]> inputData;
    HashMap<String, String> inputJson;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        divider = findViewById(R.id.divider);
        b = getIntent().getExtras();
        inputData=new HashMap<>();
        inputJson=new HashMap<>();
        System.out.println(b.getString("key"));
        if (b.getString("key").indexOf("UploadsTest") != -1) {
            findViewById(R.id.AddTest).setVisibility(View.VISIBLE);
            findViewById(R.id.form2).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.AddReq).setVisibility(View.VISIBLE);
            findViewById(R.id.form).setVisibility(View.VISIBLE);
        }
        AsyncUploader uploadFileToServer = new AsyncUploader();
        uploadFileToServer.execute("getInfo");
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    public void FirstSectionAdd (View v) {
        LinearLayout generalStagesContent = findViewById(R.id.generalStagesContent);


        View newvd = new View(getApplicationContext());
        newvd.setLayoutParams(divider.getLayoutParams());
        newvd.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        newvd.setBackgroundResource(R.drawable.dotted_line);

        TextView examGeneralStagesName = (TextView) findViewById(R.id.examGeneralStagesName);
        TextView newtv1  = new TextView(getApplicationContext());
        newtv1.setLayoutParams(examGeneralStagesName.getLayoutParams());
        examGeneralStagesNameInput = (EditText) findViewById(R.id.examGeneralStagesNameInput);
        EditText newet1 = new EditText(getApplicationContext());
        newet1.setLayoutParams(examGeneralStagesNameInput.getLayoutParams());

        TextView examGeneralStagesDate = (TextView) findViewById(R.id.examGeneralStagesDate);
        TextView newtv2 = new TextView(getApplicationContext());
        newtv2.setLayoutParams(examGeneralStagesDate.getLayoutParams());

        LinearLayout examGeneralStagesDateInputHolder = findViewById(R.id.examGeneralStagesDateInputHolder);
        LinearLayout newll = new LinearLayout(getApplicationContext());
        newll.setLayoutParams(examGeneralStagesDateInputHolder.getLayoutParams());

        RelativeLayout examGeneralStagesDateInputHolderRel1 = findViewById(R.id.examGeneralStagesDateInputHolderRel1);
        RelativeLayout examGeneralStagesDateInputHolderRel2 = findViewById(R.id.examGeneralStagesDateInputHolderRel2);
        RelativeLayout newrl1 = new RelativeLayout(getApplicationContext());
        newrl1.setLayoutParams(examGeneralStagesDateInputHolderRel1.getLayoutParams());
        RelativeLayout newrl2 = new RelativeLayout(getApplicationContext());
        newrl2.setLayoutParams(examGeneralStagesDateInputHolderRel2.getLayoutParams());

        examGeneralStagesDateInputF =  (EditText) findViewById(R.id.examGeneralStagesDateInputF);
        examGeneralStagesDateInputT =  (EditText) findViewById(R.id.examGeneralStagesDateInputT);
        EditText newet2 = new EditText(getApplicationContext());
        newet2.setLayoutParams(examGeneralStagesDateInputF.getLayoutParams());
        EditText newet3 = new EditText(getApplicationContext());
        newet3.setLayoutParams(examGeneralStagesDateInputT.getLayoutParams());


        ImageButton examGeneralStagesDateInputFButton = findViewById(R.id.examGeneralStagesDateInputFButton);
        ImageButton examGeneralStagesDateInputTButton = findViewById(R.id.examGeneralStagesDateInputTButton);
        ImageButton newib1 = new ImageButton(getApplicationContext());
        newib1.setLayoutParams(examGeneralStagesDateInputFButton.getLayoutParams());
        ImageButton newib2 = new ImageButton(getApplicationContext());
        newib2.setLayoutParams(examGeneralStagesDateInputTButton.getLayoutParams());

        TextView examGeneralStagesResult = (TextView) findViewById(R.id.examGeneralStagesResult);
        examGeneralStagesResultInput =  (EditText) findViewById(R.id.examGeneralStagesResultInput);
        TextView newtv3 = new TextView(getApplicationContext());
        newtv3.setLayoutParams(examGeneralStagesResult.getLayoutParams());
        EditText newet4 = new EditText(getApplicationContext());
        newet4.setLayoutParams(examGeneralStagesResultInput.getLayoutParams());

        newet1.setBackgroundResource(R.drawable.edit_text);
        newet2.setBackgroundResource(R.drawable.edit_text);
        newet3.setBackgroundResource(R.drawable.edit_text);
        newet4.setBackgroundResource(R.drawable.edit_text);

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (6*scale + 0.5f);

        newet1.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet2.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet3.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet4.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

        newet1.setEms(10);
        newet2.setEms(10);
        newet3.setEms(10);
        newet4.setEms(10);

        newet2.setTag("data");
        newet3.setTag("data");

        newet1.setHint("Название");
        newet2.setHint("Начало");
        newet3.setHint("Окончание");
        newet4.setHint("Результат");

        newet1.setTypeface(examGeneralStagesResultInput.getTypeface());
        newet2.setTypeface(examGeneralStagesResultInput.getTypeface());
        newet3.setTypeface(examGeneralStagesResultInput.getTypeface());
        newet4.setTypeface(examGeneralStagesResultInput.getTypeface());

        newet1.setInputType(InputType.TYPE_CLASS_TEXT );
        newet2.setInputType(InputType.TYPE_CLASS_TEXT );
        newet3.setInputType(InputType.TYPE_CLASS_TEXT );
        newet4.setInputType(InputType.TYPE_CLASS_TEXT );

        newet1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        newib1.setTag(1);
        newib2.setTag(2);

        newib1.setBackgroundResource(R.drawable.calendar);
        newib2.setBackgroundResource(R.drawable.calendar);

        newib1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ShowDatePicker(view);
            }
        });
        newib2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ShowDatePicker(view);
            }
        });

        newtv1.setText("Наименование мероприятия");
        newtv2.setText("Дата");
        newtv3.setText("Результат от реализации мероприятия");

        newtv1.setTextColor(getResources().getColor(R.color.black));
        newtv2.setTextColor(getResources().getColor(R.color.black));
        newtv3.setTextColor(getResources().getColor(R.color.black));

        newtv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        newtv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        newtv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        generalStagesContent.addView(newvd);
        generalStagesContent.addView(newtv1);
        generalStagesContent.addView(newet1);

        generalStagesContent.addView(newtv2);
        generalStagesContent.addView(newll);

        generalStagesContent.addView(newtv3);
        generalStagesContent.addView(newet4);



        newll.addView(newrl1);
        newll.addView(newrl2);

        newrl1.addView(newet2);
        newrl1.addView(newib1);

        newrl2.addView(newet3);
        newrl2.addView(newib2);
    }

    public void FirstSectionRemove (View v) {
        LinearLayout generalStagesContent = findViewById(R.id.generalStagesContent);
        if (generalStagesContent.getChildCount() != 6) {
            for (int i = 0; i < 7; i++) {
                generalStagesContent.removeViewAt(generalStagesContent.getChildCount()-1);
            }
        }
    }

    public void OtherSectionsAdd (View v) {
        LinearLayout general, oldll1, oldll2;
        EditText oldet1, oldet2, oldet3, oldet4, oldet5;
        TextView oldtv1, oldtv2;

        LinearLayout newll1, newll2;
        EditText newet1, newet2, newet3, newet4, newet5;
        TextView newtv1, newtv2;

        View newvd = new View(getApplicationContext());
        newvd.setLayoutParams(divider.getLayoutParams());
        newvd.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        newvd.setBackgroundResource(R.drawable.dotted_line);

        newll1 = new LinearLayout(getApplicationContext());
        newll2 = new LinearLayout(getApplicationContext());
        newet1 = new EditText(getApplicationContext());
        newet2 = new EditText(getApplicationContext());
        newet3 = new EditText(getApplicationContext());
        newet4 = new EditText(getApplicationContext());
        newet5 = new EditText(getApplicationContext());
        newtv1 = new TextView(getApplicationContext());
        newtv2 = new TextView(getApplicationContext());


        switch (Integer.parseInt(v.getTag().toString())) {
            case 2:
                general = findViewById(R.id.projCostsContent);
                oldll1 = findViewById(R.id.projCostsByQuarterInputHolder1);
                oldll2 = findViewById(R.id.projCostsByQuarterInputHolder2);
                oldet1 = findViewById(R.id.projCostsNameInput);
                oldet2 = findViewById(R.id.projCostsByQuarterInput1);
                oldet3 = findViewById(R.id.projCostsByQuarterInput2);
                oldet4 = findViewById(R.id.projCostsByQuarterInput3);
                oldet5 = findViewById(R.id.projCostsByQuarterInput4);

                oldtv1 = findViewById(R.id.projCostsName);
                oldtv2 = findViewById(R.id.projCostsByQuarter);

                break;
            case 3:
                general = findViewById(R.id.projFinancingSourcesContent);
                oldll1 = findViewById(R.id.projFinancingSourcesByQuarterInputHolder1);
                oldll2 = findViewById(R.id.projFinancingSourcesByQuarterInputHolder2);

                oldet1 = findViewById(R.id.projFinancingSourcesNameInput);
                oldet2 = findViewById(R.id.projFinancingSourcesByQuarterInput1);
                oldet3 = findViewById(R.id.projFinancingSourcesByQuarterInput2);
                oldet4 = findViewById(R.id.projFinancingSourcesByQuarterInput3);
                oldet5 = findViewById(R.id.projFinancingSourcesByQuarterInput4);

                oldtv1 = findViewById(R.id.projFinancingSourcesName);
                oldtv2 = findViewById(R.id.projFinancingSourcesByQuarter);
                break;
            case 4:
                general = findViewById(R.id.projParamsContent);
                oldll1 = findViewById(R.id.projParamsProfitInputHolder1);
                oldll2 = findViewById(R.id.projParamsProfitInputHolder2);

                oldet1 = findViewById(R.id.projParamsProductsTypesInput);
                oldet2 = findViewById(R.id.projParamsProfitInput1);
                oldet3 = findViewById(R.id.projParamsProfitInput2);
                oldet4 = findViewById(R.id.projParamsProfitInput3);
                oldet5 = findViewById(R.id.projParamsProfitInput4);

                oldtv1 = findViewById(R.id.projParamsProductsTypes);
                oldtv2 = findViewById(R.id.projParamsProfit);
                break;
            case 5:
                general = findViewById(R.id.projParamsContent2);
                oldll1 = findViewById(R.id.projParamsProfitInputHolder3);
                oldll2 = findViewById(R.id.projParamsProfitInputHolder4);

                oldet1 = findViewById(R.id.projParamsProductsTypesInput2);
                oldet2 = findViewById(R.id.projParamsProfitInput5);
                oldet3 = findViewById(R.id.projParamsProfitInput6);
                oldet4 = findViewById(R.id.projParamsProfitInput7);
                oldet5 = findViewById(R.id.projParamsProfitInput8);

                oldtv1 = findViewById(R.id.projParamsProductsTypes2);
                oldtv2 = findViewById(R.id.projParamsProfit2);
                break;
            default:
                return;
        }

        newll1.setLayoutParams(oldll1.getLayoutParams());
        newll2.setLayoutParams(oldll2.getLayoutParams());
        newet1.setLayoutParams(oldet1.getLayoutParams());
        newet2.setLayoutParams(oldet2.getLayoutParams());
        newet3.setLayoutParams(oldet3.getLayoutParams());
        newet4.setLayoutParams(oldet4.getLayoutParams());
        newet5.setLayoutParams(oldet5.getLayoutParams());
        newtv1.setLayoutParams(oldtv1.getLayoutParams());
        newtv2.setLayoutParams(oldtv2.getLayoutParams());

        newet1.setBackgroundResource(R.drawable.edit_text);
        newet2.setBackgroundResource(R.drawable.edit_text);
        newet3.setBackgroundResource(R.drawable.edit_text);
        newet4.setBackgroundResource(R.drawable.edit_text);
        newet5.setBackgroundResource(R.drawable.edit_text);

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (6*scale + 0.5f);

        newet1.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet2.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet3.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet4.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet5.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

        newet1.setEms(10);
        newet2.setEms(10);
        newet3.setEms(10);
        newet4.setEms(10);
        newet5.setEms(10);

        newet2.setTag("data");
        newet3.setTag("data");
        newet4.setTag("data");
        newet5.setTag("data");

        newet1.setHint(oldet1.getHint());
        newet2.setHint(oldet2.getHint());
        newet3.setHint(oldet3.getHint());
        newet4.setHint(oldet4.getHint());
        newet5.setHint(oldet5.getHint());

        newet1.setTypeface(oldet1.getTypeface());
        newet2.setTypeface(oldet1.getTypeface());
        newet3.setTypeface(oldet1.getTypeface());
        newet4.setTypeface(oldet1.getTypeface());
        newet5.setTypeface(oldet1.getTypeface());

        newet1.setInputType(InputType.TYPE_CLASS_TEXT );
        newet2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        newet3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        newet4.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        newet5.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        newet1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);


        newtv1.setText(oldtv1.getText());
        newtv2.setText(oldtv2.getText());

        newtv1.setTextColor(getResources().getColor(R.color.black));
        newtv2.setTextColor(getResources().getColor(R.color.black));

        newtv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        newtv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        general.addView(newvd);
        general.addView(newtv1);
        general.addView(newet1);

        general.addView(newtv2);
        general.addView(newll1);
        general.addView(newll2);

        newll1.addView(newet2);
        newll1.addView(newet3);
        newll2.addView(newet4);
        newll2.addView(newet5);
    }

    public void OtherSectionsRemove (View v) {
        LinearLayout general;
        switch (Integer.parseInt(v.getTag().toString())) {
            case 2:
                general = findViewById(R.id.projCostsContent);
                break;
            case 3:
                general = findViewById(R.id.projFinancingSourcesContent);
                break;
            case 4:
                general = findViewById(R.id.projParamsContent);
                break;
            case 5:
                general = findViewById(R.id.projParamsContent2);
                break;
            default:
                return;
        }

        if (general.getChildCount() != 5) {
            for (int i = 0; i < 6; i++) {
                general.removeViewAt(general.getChildCount()-1);
            }
        }
    }


    public void SendInfo (View v) {
        LinearLayout general = findViewById(R.id.generalField);
        ArrayList<View> iter = getAllChildren (general);
        int i = 0;
        boolean Flag = true;
        ArrayList<String> top = new ArrayList<String>();
        System.out.println(iter.size());
        for(; ;i+=1) {
            System.out.println(1);
            try{
                if (iter.get(i) instanceof EditText) {
                    top.add(((EditText) iter.get(i)).getText().toString());
                } else if (iter.get(i).getId() == R.id.generalStages) {
                    break;
                }
            } catch (Exception e) {

            }
        }

        ArrayList<String> firstTab = new ArrayList<String>();
        for(; ;i+=1) {
            System.out.println(2);
            try {
                if (iter.get(i) instanceof EditText) {
                    firstTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
                            Date date = format.parse(((EditText) iter.get(i)).getText().toString());
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } catch (Exception e) {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check1) {
                    break;
                }
                System.out.println(getResources().getResourceName(iter.get(i).getId()) + " " + iter.get(i).getId() + " " + R.id.check1);
            }catch (Exception e) {

            }
        }

        ArrayList<String> secondTab = new ArrayList<String>();
        for(; ;i+=1) {
            System.out.println(3);
            try {
                if (iter.get(i) instanceof EditText) {
                    secondTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check2) {
                    break;
                }
            } catch (Exception e) {

            }
        }

        ArrayList<String> thirdTab = new ArrayList<String>();
        for(; ;i+=1) {
            System.out.println(4);
            try {
                if (iter.get(i) instanceof EditText) {
                    thirdTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check3) {
                    break;
                }
            } catch (Exception e) {

            }
        }

        ArrayList<String> fortyTab = new ArrayList<String>();
        for(; ;i+=1) {
            System.out.println(5);
            try {
                if (iter.get(i) instanceof EditText) {
                    fortyTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check4) {
                    break;
                }
            } catch (Exception e) {}
        }

        ArrayList<String> fiftyTab = new ArrayList<String>();
        for(; ;i+=1) {
            System.out.println(6);
            try {
                if (iter.get(i) instanceof EditText) {
                    fiftyTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check5) {
                    break;
                }
            } catch (Exception e) {}
        }

        ArrayList<String> sixtyTab = new ArrayList<String>();
        for(; ;i+=1) {
            System.out.println(7);
            try {
                if (iter.get(i) instanceof EditText) {
                    sixtyTab.add(((EditText) iter.get(i)).getText().toString());
                } else if (iter.get(i).getId() == R.id.button) {
                    break;
                }
            } catch (Exception e) {}
        }
        if (Flag) {
            HashMap<String,ArrayList> data = new HashMap<String,ArrayList>();
            data.put("title", top);
            data.put("tab1", firstTab);
            data.put("tab2", secondTab);
            data.put("tab3", thirdTab);
            data.put("tab4", fortyTab);
            data.put("tab5", fiftyTab);
            data.put("tab6", sixtyTab);
            Gson gson = new Gson();
            Type typeObject = new TypeToken<HashMap>() {}.getType();
            String gsonData = gson.toJson(data, typeObject);


            inputJson.put("jsonChange",gsonData);

            AsyncUploader uploadFileToServer = new AsyncUploader();
            uploadFileToServer.execute("jsonChange");
            Clear ();
        } else {
            Toast.makeText(this, "Вам необходимо корректно заполнить вывеленные поля", Toast.LENGTH_LONG).show();
        }
    }

    public void Clear () {
        LinearLayout generalStagesContent = findViewById(R.id.generalStagesContent);
        while (generalStagesContent.getChildCount() != 6) {
            generalStagesContent.removeViewAt(generalStagesContent.getChildCount()-1);
        }
        LinearLayout general;
        general = findViewById(R.id.projCostsContent);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.projFinancingSourcesContent);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.projParamsContent);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.projParamsContent2);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.generalField);
        ArrayList<View> iter = getAllChildren (general);

        int i = 0;
        for(; ;i+=1) {
            try{
                if (iter.get(i) instanceof EditText) {
                    ((EditText) iter.get(i)).setText("");
                } else if (iter.get(i).getId() == R.id.generalStages) {
                    break;
                }
            } catch (Exception e) {

            }
        }

        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check1) {
                break;
            }
        }

        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check2) {
                break;
            }
        }

        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check3) {
                break;
            }
        }


        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check4) {
                break;
            }
        }


        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check5) {
                break;
            }
        }
        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.button) {
                break;
            }
        }
    }



    public void FirstSectionAdd2 (View v) {
        LinearLayout generalStagesContent = findViewById(R.id.generalStagesContent2);


        View newvd = new View(getApplicationContext());
        newvd.setLayoutParams(divider.getLayoutParams());
        newvd.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        newvd.setBackgroundResource(R.drawable.dotted_line);

        TextView examGeneralStagesName = (TextView) findViewById(R.id.examGeneralStagesName2);
        TextView newtv1  = new TextView(getApplicationContext());
        newtv1.setLayoutParams(examGeneralStagesName.getLayoutParams());
        examGeneralStagesNameInput = (EditText) findViewById(R.id.examGeneralStagesNameInput2);
        EditText newet1 = new EditText(getApplicationContext());
        newet1.setLayoutParams(examGeneralStagesNameInput.getLayoutParams());

        TextView examGeneralStagesDate = (TextView) findViewById(R.id.examGeneralStagesDate2);
        TextView newtv2 = new TextView(getApplicationContext());
        newtv2.setLayoutParams(examGeneralStagesDate.getLayoutParams());

        LinearLayout examGeneralStagesDateInputHolder = findViewById(R.id.examGeneralStagesDateInputHolder2);
        LinearLayout newll = new LinearLayout(getApplicationContext());
        newll.setLayoutParams(examGeneralStagesDateInputHolder.getLayoutParams());

        RelativeLayout examGeneralStagesDateInputHolderRel1 = findViewById(R.id.examGeneralStagesDateInputHolderRel12);
        RelativeLayout examGeneralStagesDateInputHolderRel2 = findViewById(R.id.examGeneralStagesDateInputHolderRel22);
        RelativeLayout newrl1 = new RelativeLayout(getApplicationContext());
        newrl1.setLayoutParams(examGeneralStagesDateInputHolderRel1.getLayoutParams());
        RelativeLayout newrl2 = new RelativeLayout(getApplicationContext());
        newrl2.setLayoutParams(examGeneralStagesDateInputHolderRel2.getLayoutParams());

        examGeneralStagesDateInputF =  (EditText) findViewById(R.id.examGeneralStagesDateInputF2);
        examGeneralStagesDateInputT =  (EditText) findViewById(R.id.examGeneralStagesDateInputT2);
        EditText newet2 = new EditText(getApplicationContext());
        newet2.setLayoutParams(examGeneralStagesDateInputF.getLayoutParams());
        EditText newet3 = new EditText(getApplicationContext());
        newet3.setLayoutParams(examGeneralStagesDateInputT.getLayoutParams());


        ImageButton examGeneralStagesDateInputFButton = findViewById(R.id.examGeneralStagesDateInputFButton2);
        ImageButton examGeneralStagesDateInputTButton = findViewById(R.id.examGeneralStagesDateInputTButton2);
        ImageButton newib1 = new ImageButton(getApplicationContext());
        newib1.setLayoutParams(examGeneralStagesDateInputFButton.getLayoutParams());
        ImageButton newib2 = new ImageButton(getApplicationContext());
        newib2.setLayoutParams(examGeneralStagesDateInputTButton.getLayoutParams());

        TextView examGeneralStagesResult = (TextView) findViewById(R.id.examGeneralStagesResult2);
        examGeneralStagesResultInput =  (EditText) findViewById(R.id.examGeneralStagesResultInput2);
        TextView newtv3 = new TextView(getApplicationContext());
        newtv3.setLayoutParams(examGeneralStagesResult.getLayoutParams());
        EditText newet4 = new EditText(getApplicationContext());
        newet4.setLayoutParams(examGeneralStagesResultInput.getLayoutParams());

        newet1.setBackgroundResource(R.drawable.edit_text);
        newet2.setBackgroundResource(R.drawable.edit_text);
        newet3.setBackgroundResource(R.drawable.edit_text);
        newet4.setBackgroundResource(R.drawable.edit_text);

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (6*scale + 0.5f);

        newet1.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet2.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet3.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet4.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

        newet1.setEms(10);
        newet2.setEms(10);
        newet3.setEms(10);
        newet4.setEms(10);

        newet2.setTag("data");
        newet3.setTag("data");

        newet1.setHint("Название");
        newet2.setHint("Начало");
        newet3.setHint("Окончание");
        newet4.setHint("Результат");

        newet1.setTypeface(examGeneralStagesResultInput.getTypeface());
        newet2.setTypeface(examGeneralStagesResultInput.getTypeface());
        newet3.setTypeface(examGeneralStagesResultInput.getTypeface());
        newet4.setTypeface(examGeneralStagesResultInput.getTypeface());

        newet1.setInputType(InputType.TYPE_CLASS_TEXT );
        newet2.setInputType(InputType.TYPE_CLASS_TEXT );
        newet3.setInputType(InputType.TYPE_CLASS_TEXT );
        newet4.setInputType(InputType.TYPE_CLASS_TEXT );

        newet1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        newib1.setTag(1);
        newib2.setTag(2);

        newib1.setBackgroundResource(R.drawable.calendar);
        newib2.setBackgroundResource(R.drawable.calendar);

        newib1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ShowDatePicker(view);
            }
        });
        newib2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ShowDatePicker(view);
            }
        });

        newtv1.setText("Наименование мероприятия");
        newtv2.setText("Дата");
        newtv3.setText("Результат от реализации мероприятия");

        newtv1.setTextColor(getResources().getColor(R.color.black));
        newtv2.setTextColor(getResources().getColor(R.color.black));
        newtv3.setTextColor(getResources().getColor(R.color.black));

        newtv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        newtv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        newtv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        generalStagesContent.addView(newvd);
        generalStagesContent.addView(newtv1);
        generalStagesContent.addView(newet1);

        generalStagesContent.addView(newtv2);
        generalStagesContent.addView(newll);

        generalStagesContent.addView(newtv3);
        generalStagesContent.addView(newet4);



        newll.addView(newrl1);
        newll.addView(newrl2);

        newrl1.addView(newet2);
        newrl1.addView(newib1);

        newrl2.addView(newet3);
        newrl2.addView(newib2);
    }

    public void FirstSectionRemove2 (View v) {
        LinearLayout generalStagesContent = findViewById(R.id.generalStagesContent2);
        if (generalStagesContent.getChildCount() != 6) {
            for (int i = 0; i < 7; i++) {
                generalStagesContent.removeViewAt(generalStagesContent.getChildCount()-1);
            }
        }
    }

    public void OtherSectionsAdd2 (View v) {
        LinearLayout general, oldll1, oldll2;
        EditText oldet1, oldet2, oldet3, oldet4, oldet5;
        TextView oldtv1, oldtv2;

        LinearLayout newll1, newll2;
        EditText newet1, newet2, newet3, newet4, newet5;
        TextView newtv1, newtv2;

        View newvd = new View(getApplicationContext());
        newvd.setLayoutParams(divider.getLayoutParams());
        newvd.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        newvd.setBackgroundResource(R.drawable.dotted_line);

        newll1 = new LinearLayout(getApplicationContext());
        newll2 = new LinearLayout(getApplicationContext());
        newet1 = new EditText(getApplicationContext());
        newet2 = new EditText(getApplicationContext());
        newet3 = new EditText(getApplicationContext());
        newet4 = new EditText(getApplicationContext());
        newet5 = new EditText(getApplicationContext());
        newtv1 = new TextView(getApplicationContext());
        newtv2 = new TextView(getApplicationContext());


        switch (Integer.parseInt(v.getTag().toString())) {
            case 2:
                general = findViewById(R.id.projCostsContent2);
                oldll1 = findViewById(R.id.projCostsByQuarterInputHolder12);
                oldll2 = findViewById(R.id.projCostsByQuarterInputHolder22);
                oldet1 = findViewById(R.id.projCostsNameInput2);
                oldet2 = findViewById(R.id.projCostsByQuarterInput12);
                oldet3 = findViewById(R.id.projCostsByQuarterInput22);
                oldet4 = findViewById(R.id.projCostsByQuarterInput32);
                oldet5 = findViewById(R.id.projCostsByQuarterInput42);

                oldtv1 = findViewById(R.id.projCostsName2);
                oldtv2 = findViewById(R.id.projCostsByQuarter2);

                break;
            case 3:
                general = findViewById(R.id.projFinancingSourcesContent2);
                oldll1 = findViewById(R.id.projFinancingSourcesByQuarterInputHolder12);
                oldll2 = findViewById(R.id.projFinancingSourcesByQuarterInputHolder22);

                oldet1 = findViewById(R.id.projFinancingSourcesNameInput2);
                oldet2 = findViewById(R.id.projFinancingSourcesByQuarterInput12);
                oldet3 = findViewById(R.id.projFinancingSourcesByQuarterInput22);
                oldet4 = findViewById(R.id.projFinancingSourcesByQuarterInput32);
                oldet5 = findViewById(R.id.projFinancingSourcesByQuarterInput42);

                oldtv1 = findViewById(R.id.projFinancingSourcesName2);
                oldtv2 = findViewById(R.id.projFinancingSourcesByQuarter2);
                break;
            case 4:
                general = findViewById(R.id.projParamsContent22);
                oldll1 = findViewById(R.id.projParamsProfitInputHolder12);
                oldll2 = findViewById(R.id.projParamsProfitInputHolder22);

                oldet1 = findViewById(R.id.projParamsProductsTypesInput22);
                oldet2 = findViewById(R.id.projParamsProfitInput12);
                oldet3 = findViewById(R.id.projParamsProfitInput22);
                oldet4 = findViewById(R.id.projParamsProfitInput32);
                oldet5 = findViewById(R.id.projParamsProfitInput42);

                oldtv1 = findViewById(R.id.projParamsProductsTypes22);
                oldtv2 = findViewById(R.id.projParamsProfit22);
                break;
            case 5:
                general = findViewById(R.id.projParamsContent222);
                oldll1 = findViewById(R.id.projParamsProfitInputHolder32);
                oldll2 = findViewById(R.id.projParamsProfitInputHolder42);

                oldet1 = findViewById(R.id.projParamsProductsTypesInput222);
                oldet2 = findViewById(R.id.projParamsProfitInput52);
                oldet3 = findViewById(R.id.projParamsProfitInput62);
                oldet4 = findViewById(R.id.projParamsProfitInput72);
                oldet5 = findViewById(R.id.projParamsProfitInput82);

                oldtv1 = findViewById(R.id.projParamsProductsTypes222);
                oldtv2 = findViewById(R.id.projParamsProfit222);
                break;
            default:
                return;
        }

        newll1.setLayoutParams(oldll1.getLayoutParams());
        newll2.setLayoutParams(oldll2.getLayoutParams());
        newet1.setLayoutParams(oldet1.getLayoutParams());
        newet2.setLayoutParams(oldet2.getLayoutParams());
        newet3.setLayoutParams(oldet3.getLayoutParams());
        newet4.setLayoutParams(oldet4.getLayoutParams());
        newet5.setLayoutParams(oldet5.getLayoutParams());
        newtv1.setLayoutParams(oldtv1.getLayoutParams());
        newtv2.setLayoutParams(oldtv2.getLayoutParams());

        newet1.setBackgroundResource(R.drawable.edit_text);
        newet2.setBackgroundResource(R.drawable.edit_text);
        newet3.setBackgroundResource(R.drawable.edit_text);
        newet4.setBackgroundResource(R.drawable.edit_text);
        newet5.setBackgroundResource(R.drawable.edit_text);

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (6*scale + 0.5f);

        newet1.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet2.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet3.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet4.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
        newet5.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

        newet1.setEms(10);
        newet2.setEms(10);
        newet3.setEms(10);
        newet4.setEms(10);
        newet5.setEms(10);

        newet2.setTag("data");
        newet3.setTag("data");
        newet4.setTag("data");
        newet5.setTag("data");

        newet1.setHint(oldet1.getHint());
        newet2.setHint(oldet2.getHint());
        newet3.setHint(oldet3.getHint());
        newet4.setHint(oldet4.getHint());
        newet5.setHint(oldet5.getHint());

        newet1.setTypeface(oldet1.getTypeface());
        newet2.setTypeface(oldet1.getTypeface());
        newet3.setTypeface(oldet1.getTypeface());
        newet4.setTypeface(oldet1.getTypeface());
        newet5.setTypeface(oldet1.getTypeface());

        newet1.setInputType(InputType.TYPE_CLASS_TEXT );
        newet2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        newet3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        newet4.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        newet5.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        newet1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        newet5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);


        newtv1.setText(oldtv1.getText());
        newtv2.setText(oldtv2.getText());

        newtv1.setTextColor(getResources().getColor(R.color.black));
        newtv2.setTextColor(getResources().getColor(R.color.black));

        newtv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        newtv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        general.addView(newvd);
        general.addView(newtv1);
        general.addView(newet1);

        general.addView(newtv2);
        general.addView(newll1);
        general.addView(newll2);

        newll1.addView(newet2);
        newll1.addView(newet3);
        newll2.addView(newet4);
        newll2.addView(newet5);
    }

    public void OtherSectionsRemove2 (View v) {
        LinearLayout general;
        switch (Integer.parseInt(v.getTag().toString())) {
            case 2:
                general = findViewById(R.id.projCostsContent2);
                break;
            case 3:
                general = findViewById(R.id.projFinancingSourcesContent2);
                break;
            case 4:
                general = findViewById(R.id.projParamsContent22);
                break;
            case 5:
                general = findViewById(R.id.projParamsContent222);
                break;
            default:
                return;
        }

        if (general.getChildCount() != 5) {
            for (int i = 0; i < 6; i++) {
                general.removeViewAt(general.getChildCount()-1);
            }
        }
    }


    public void SendInfo2 (View v) {
        LinearLayout general = findViewById(R.id.generalField2);
        ArrayList<View> iter = getAllChildren (general);
        int i = 0;
        boolean Flag = true;
        ArrayList<String> top = new ArrayList<String>();
        for(; ;i+=1) {
            try{
                if (iter.get(i) instanceof EditText) {
                    top.add(((EditText) iter.get(i)).getText().toString());
                } else if (iter.get(i).getId() == R.id.generalStages2) {
                    break;
                }
            } catch (Exception e) {

            }
        }

        ArrayList<String> firstTab = new ArrayList<String>();
        for(; ;i+=1) {
            try {
                if (iter.get(i) instanceof EditText) {
                    firstTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
                            Date date = format.parse(((EditText) iter.get(i)).getText().toString());
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } catch (Exception e) {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check12) {
                    break;
                }
            }catch (Exception e) {

            }
        }

        ArrayList<String> secondTab = new ArrayList<String>();
        for(; ;i+=1) {
            try {
                if (iter.get(i) instanceof EditText) {
                    secondTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check22) {
                    break;
                }
            } catch (Exception e) {

            }
        }

        ArrayList<String> thirdTab = new ArrayList<String>();
        for(; ;i+=1) {
            try {
                if (iter.get(i) instanceof EditText) {
                    thirdTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check32) {
                    break;
                }
            } catch (Exception e) {

            }
        }

        ArrayList<String> fortyTab = new ArrayList<String>();
        for(; ;i+=1) {
            try {
                if (iter.get(i) instanceof EditText) {
                    fortyTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check42) {
                    break;
                }
            } catch (Exception e) {}
        }

        ArrayList<String> fiftyTab = new ArrayList<String>();
        for(; ;i+=1) {
            try {
                if (iter.get(i) instanceof EditText) {
                    fiftyTab.add(((EditText) iter.get(i)).getText().toString());
                    if (iter.get(i).getTag().toString().equals("data")) {
                        if (((EditText) iter.get(i)).getText().toString().matches("[0-9]+\\.[0-9]+") || ((EditText) iter.get(i)).getText().toString().matches("[0-9]+")) {
                            ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                            for (int j = 0; j < toRemoveSpans.length; j++) {
                                ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                            }
                        } else {
                            Flag = false;
                            ((EditText) iter.get(i)).getText().setSpan(new ErrorSpan(getResources()), 0, ((EditText) iter.get(i)).getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else if (iter.get(i).getId() == R.id.check52) {
                    break;
                }
            } catch (Exception e) {}
        }

        ArrayList<String> sixtyTab = new ArrayList<String>();
        for(; ;i+=1) {
            try {
                if (iter.get(i) instanceof EditText) {
                    sixtyTab.add(((EditText) iter.get(i)).getText().toString());
                } else if (iter.get(i).getId() == R.id.button2) {
                    break;
                }
            } catch (Exception e) {}
        }
        if (Flag) {
            HashMap<String,ArrayList> data = new HashMap<String,ArrayList>();
            data.put("title", top);
            data.put("tab1", firstTab);
            data.put("tab2", secondTab);
            data.put("tab3", thirdTab);
            data.put("tab4", fortyTab);
            data.put("tab5", fiftyTab);
            data.put("tab6", sixtyTab);
            Gson gson = new Gson();
            Type typeObject = new TypeToken<HashMap>() {}.getType();
            String gsonData = gson.toJson(data, typeObject);

            inputJson.put("jsonChange",gsonData);

            AsyncUploader uploadFileToServer = new AsyncUploader();
            uploadFileToServer.execute("jsonChange");
            Clear2 ();
        } else {
            Toast.makeText(this, "Вам необходимо корректно заполнить вывеленные поля", Toast.LENGTH_LONG).show();
        }
    }

    public void Clear2 () {
        LinearLayout generalStagesContent = findViewById(R.id.generalStagesContent2);
        while (generalStagesContent.getChildCount() != 6) {
            generalStagesContent.removeViewAt(generalStagesContent.getChildCount()-1);
        }
        LinearLayout general;
        general = findViewById(R.id.projCostsContent2);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.projFinancingSourcesContent2);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.projParamsContent22);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.projParamsContent222);
        while (general.getChildCount() != 5) {
            general.removeViewAt(general.getChildCount()-1);
        }
        general = findViewById(R.id.generalField2);
        ArrayList<View> iter = getAllChildren (general);

        int i = 0;
        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.generalStages2) {
                break;
            }

        }

        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check12) {
                break;
            }
        }

        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check22) {
                break;
            }
        }


        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check32) {
                break;
            }
        }

        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check42) {
                break;
            }
        }


        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ErrorSpan[] toRemoveSpans = ((EditText) iter.get(i)).getText().getSpans(0, ((EditText) iter.get(i)).getText().length(), ErrorSpan.class);
                for (int j = 0; j < toRemoveSpans.length; j++) {
                    ((EditText) iter.get(i)).getText().removeSpan(toRemoveSpans[j]);
                }
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.check52) {
                break;
            }
        }
        for(; ;i+=1) {
            if (iter.get(i) instanceof EditText) {
                ((EditText) iter.get(i)).setText("");
            } else if (iter.get(i).getId() == R.id.button2) {
                break;
            }
        }
    }

    public void ShowDatePicker (View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(GetInfo.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        for (int itemPos = 0; itemPos < ((ViewGroup )v.getParent()).getChildCount(); itemPos++) {
                            View c = ((ViewGroup )v.getParent()).getChildAt(itemPos);
                            if (c instanceof EditText) {
                                ((EditText)c).setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            }
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void OnAddButton (View v) {
        showFileChooser();
    }

    private void showFileChooser() {
        for (int i = 0; i < 5; i++) {
            try {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);
                break;
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            for (int i = 0; i < 5; i++) {
                try {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                InputStream iStream = null;
                                try {
                                    iStream = getContentResolver().openInputStream(uri);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (requestCode == 1) {
                                    inputData.put("fileChange",getBytes(iStream));
                                }
                                Toast.makeText(this,"Успешно", Toast.LENGTH_LONG).show();
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        InputStream iStream = null;
                        try {
                            iStream = getContentResolver().openInputStream(uri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (requestCode == 1) {
                            inputData.put("fileChange",getBytes(iStream));
                        }
                        Toast.makeText(this,"Успешно", Toast.LENGTH_LONG).show();
                    }
                    break;
                } catch (Exception e) {
                }
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void OnClickButton1 (View v) {
        AsyncUploader uploadFileToServer = new AsyncUploader();
        uploadFileToServer.execute("fileChange");
    }

    public void FinisThis (View v) {
        finish();
    }

    public String getValue(){
        SharedPreferences preferences = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        return preferences.getString("id", "");
    }

    private class AsyncUploader extends AsyncTask<String, String, String> {
        int StartWriting = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return uploadFile(params[0]);
        }
        @Override
        protected void  onPostExecute(String result) {

            if (StartWriting==1) {

                resultWriting (result);
            } else if (StartWriting==0) {
                resultProcess (result);
            }
            StartWriting = 0;
        }

        private String uploadFile(String requestTo) {
            if (inputData.get(requestTo) == null && !(requestTo.equals("jsonChange") || requestTo.equals("getInfo"))) return "100";
            try {
                OkHttpClient client = new OkHttpClient();
                MultipartBody.Builder buildBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("id", getValue())
                        .addFormDataPart("path", b.getString("key"));
                RequestBody requestBody;
                if (requestTo.equals("jsonChange")) {
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inputJson.get(requestTo));
                    requestBody = buildBody.addFormDataPart("file","file.json",body).build();
                } else if (requestTo.equals("getInfo")) {
                    requestBody = buildBody.build();
                } else {
                    requestBody = buildBody.addFormDataPart("file", "file.pdf",
                                RequestBody.create(MediaType.parse("text/plain"), inputData.get(requestTo))).build();
                }


                Request request = new Request.Builder()
                        .url("http://213.226.126.69:5000/" + requestTo)
                        .post(requestBody)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();
                String response_text = response.body().string();
                int status_code = response.code();
                response.body().close();
                System.out.println(status_code + " " + response_text);
                if (requestTo.equals("getInfo")) {
                    if (status_code == 201) StartWriting = 1;
                    else StartWriting = 2;
                    return response_text;
                }
                if (response_text.equals("400")) return "400";
                return "200";
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return "0";
            }
        }
    }

    public void resultProcess (String result) {
        if (result.equals("200")) {
            Toast.makeText(this,"Успешно", Toast.LENGTH_LONG).show();
            finish();
        } else if (result.equals("100")) {
            Toast.makeText(this,"Необходимо прикрепить файл", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"No internet coonection", Toast.LENGTH_LONG).show();
        }
    }

    public void resultWriting (String result) {
        try {
            JSONObject obj = new JSONObject(result);
            JSONArray tab1 = obj.getJSONArray("tab1");
            JSONArray tab2 = obj.getJSONArray("tab2");
            JSONArray tab3 = obj.getJSONArray("tab3");
            JSONArray tab4 = obj.getJSONArray("tab4");
            JSONArray tab5 = obj.getJSONArray("tab5");
            JSONArray tab6 = obj.getJSONArray("tab6");
            JSONArray title = obj.getJSONArray("title");

            if (b.getString("key").indexOf("UploadsTest") != -1) {
                View arg = new View(getApplicationContext());
                for (int i = 0; i < (tab1.length() / 4) - 1; i++) {
                    FirstSectionAdd2(arg);
                }
                arg.setTag("2");
                for (int i = 0; i < (tab2.length() / 4) - 1; i++) {
                    OtherSectionsAdd2(arg);
                }
                arg.setTag("3");
                for (int i = 0; i < (tab3.length() / 4) - 1; i++) {
                    OtherSectionsAdd2(arg);
                }
                arg.setTag("4");
                for (int i = 0; i < (tab4.length() / 4) - 1; i++) {
                    OtherSectionsAdd2(arg);
                }
                arg.setTag("5");
                for (int i = 0; i < (tab5.length() / 4) - 1; i++) {
                    OtherSectionsAdd2(arg);
                }

                LinearLayout general = findViewById(R.id.generalField2);
                ArrayList<View> iter = getAllChildren (general);

                int i = 0;
                int j = 0;
                for(;;i+=1) {
                    try{
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(title.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.generalStages2) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab1.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check12) {
                            break;
                        }
                    }catch (Exception e) {
                    }
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab2.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check22) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab3.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check32) {
                            break;
                        }
                    } catch (Exception e) {}
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab4.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check42) {
                            break;
                        }
                    } catch (Exception e) {}
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab5.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check52) {
                            break;
                        }
                    } catch (Exception e) {}
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab6.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.button2) {
                            break;
                        }
                    } catch (Exception e) {}
                }
            } else {
                View arg = new View(getApplicationContext());
                for (int i = 0; i < (tab1.length() / 4) - 1; i++) {
                    FirstSectionAdd(arg);
                }
                arg.setTag("2");
                for (int i = 0; i < (tab2.length() / 4) - 1; i++) {
                    OtherSectionsAdd(arg);
                }
                arg.setTag("3");
                for (int i = 0; i < (tab3.length() / 4) - 1; i++) {
                    OtherSectionsAdd(arg);
                }
                arg.setTag("4");
                for (int i = 0; i < (tab4.length() / 4) - 1; i++) {
                    OtherSectionsAdd(arg);
                }
                arg.setTag("5");
                for (int i = 0; i < (tab5.length() / 4) - 1; i++) {
                    OtherSectionsAdd(arg);
                }

                LinearLayout general = findViewById(R.id.generalField);
                ArrayList<View> iter = getAllChildren (general);

                int i = 0;
                int j = 0;
                for(;;i+=1) {

                    try{
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(title.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.generalStages) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab1.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check1) {
                            break;
                        }
                    }catch (Exception e) {
                    }
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab2.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check2) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab3.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check3) {
                            break;
                        }
                    } catch (Exception e) {}
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab4.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check4) {
                            break;
                        }
                    } catch (Exception e) {}
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab5.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.check5) {
                            break;
                        }
                    } catch (Exception e) {}
                }

                j = 0;
                for(; ;i+=1) {
                    try {
                        if (iter.get(i) instanceof EditText) {
                            ((EditText) iter.get(i)).setText(tab6.getString(j));
                            j++;
                        } else if (iter.get(i).getId() == R.id.button) {
                            break;
                        }
                    } catch (Exception e) {}
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}