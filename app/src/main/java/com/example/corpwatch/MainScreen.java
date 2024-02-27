package com.example.corpwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.text.ParcelableSpan;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.DynamicDrawableSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainScreen extends AppCompatActivity {
    int PERMISSIONS_CODE = 1;
    int height_f = 3;
    View f = null;
    RelativeLayout layout;
    Button bt1, bt2, bt3;
    float ActiveTxt = 18f;
    float BaseTxt = 17;
    HashMap<String, byte[]> inputData;
    HashMap<String, String> inputJson;
    Files active;
    TextView ex1tsc;
    ImageButton ex1bsc;
    LinearLayout ex1lsc;
    Files [] resultObjects;

    EditText editPrName;
    EditText editproPurpose;
    EditText examGeneralStagesNameInput;
    EditText examGeneralStagesDateInputF;
    EditText examGeneralStagesDateInputT;
    EditText examGeneralStagesResultInput;
    View divider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        layout = (RelativeLayout)findViewById(R.id.rel);
        bt1 = findViewById(R.id.button4);
        bt2 = findViewById(R.id.button5);
        bt3 = findViewById(R.id.button6);
        ex1tsc = findViewById(R.id.textView5);
        ex1bsc = findViewById(R.id.imageButton);
        ex1lsc = findViewById(R.id.example1_scroll);
        divider = findViewById(R.id.divider);

        inputData=new HashMap<>();
        inputJson=new HashMap<>();
        lineUlt(1);
        getHistory();
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

            System.out.println(gsonData);

            inputJson.put("jsonReq",gsonData);

            AsyncUploader uploadFileToServer = new AsyncUploader();
            uploadFileToServer.execute("jsonReq");
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

            inputJson.put("jsonTest",gsonData);

            AsyncUploader uploadFileToServer = new AsyncUploader();
            uploadFileToServer.execute("jsonTest");
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



    public void getHistory () {
        final StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://213.226.126.69/hist.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);
                        LinearLayout scv = findViewById(R.id.scvcd1);
                        ArrayList<View> childs = new ArrayList<View>();
                        for (int i = 0; i < scv.getChildCount(); i++) {
                            if (scv.getChildAt(i).getId() != R.id.example1_scroll){
                                childs.add (scv.getChildAt(i));
                            }
                        }
                        for (View v:childs) {
                            scv.removeView(v);
                        }
                        if (response.equals("400")) {
                            TextView elem = new TextView(getApplicationContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            elem.setLayoutParams(params);
                            elem.setTextColor(getResources().getColor(R.color.black));
                            elem.setTextAppearance(android.R.style.TextAppearance_Material_Body2);
                            elem.setTextSize(TypedValue.COMPLEX_UNIT_SP, ActiveTxt);
                            elem.setGravity(Gravity.CENTER);
                            elem.setText("Ничего не найдено");
                            scv.addView(elem);
                        } else {
                            resultObjects = new Gson().fromJson(response.toString(), Files[].class);
                            int i = 0;
                            for (Files f : resultObjects) {
                                System.out.println(f.name_file);
                                TextView elem = new TextView(getApplicationContext());
                                elem.setLayoutParams(ex1tsc.getLayoutParams());

                                ImageButton elem2 = new ImageButton((getApplicationContext()));
                                elem2.setLayoutParams(ex1bsc.getLayoutParams());

                                LinearLayout elem3 = new LinearLayout((getApplicationContext()));
                                elem3.setLayoutParams(ex1lsc.getLayoutParams());

                                elem.setTextColor(getResources().getColor(R.color.black));
                                elem.setTextAppearance(android.R.style.TextAppearance_Material_Body2);
                                elem.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                                elem.setGravity(Gravity.CENTER);
                                elem.setText(f.name_file);

                                elem2.setPadding(20,0,0,0);
                                elem2.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                                elem2.setScaleType(ImageView.ScaleType.FIT_START);
                                elem2.setImageResource (R.drawable.skrpk);
                                elem2.setTag(Integer.toString(i));
                                elem2.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view) {
                                        active = resultObjects[Integer.parseInt(view.getTag().toString())];
                                        Intent intent = new Intent(MainScreen.this, GetInfo.class);
                                        Bundle b = new Bundle();
                                        b.putString("key", active.path);
                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                });
                                i++;


                                elem3.setVerticalGravity(Gravity.CENTER_VERTICAL);
                                elem3.setHorizontalGravity(Gravity.RIGHT);
                                elem3.setOrientation(LinearLayout.HORIZONTAL);

                                elem3.addView(elem);
                                elem3.addView(elem2);
                                scv.addView(elem3);
                            }
                        }
                        findViewById(R.id.scv1).setVisibility(View.VISIBLE);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("id", getValue());
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showFileChooser(int requestCode) {
        for (int i = 0; i < 5; i++) {
            try {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, requestCode);
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
                                    inputData.put("req",getBytes(iStream));
                                } else if (requestCode == 2) {
                                    inputData.put("test",getBytes(iStream));
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
                            inputData.put("req",getBytes(iStream));
                        } else if (requestCode == 2) {
                            inputData.put("test",getBytes(iStream));
                        }
                        Toast.makeText(this,"Успешно", Toast.LENGTH_LONG).show();
                    }
                    break;
                } catch (Exception e) {
                }
            }
        }
    }


    public void onClickSlider (View v) {
        int n = Integer.parseInt(v.getTag().toString());
        lineUlt (n);

        findViewById(R.id.scv1).setVisibility(View.GONE);
        findViewById(R.id.AddReq).setVisibility(View.GONE);
        findViewById(R.id.AddTest).setVisibility(View.GONE);
        findViewById(R.id.form).setVisibility(View.GONE);
        findViewById(R.id.form2).setVisibility(View.GONE);


        bt1.setTextAppearance(android.R.style.TextAppearance_Material_Body1);
        bt2.setTextAppearance(android.R.style.TextAppearance_Material_Body1);
        bt3.setTextAppearance(android.R.style.TextAppearance_Material_Body1);


        bt1.setTextSize(TypedValue.COMPLEX_UNIT_SP,BaseTxt);
        bt2.setTextSize(TypedValue.COMPLEX_UNIT_SP,BaseTxt);
        bt3.setTextSize(TypedValue.COMPLEX_UNIT_SP,BaseTxt);
        if (n == 1) {
            bt1.setTextAppearance(android.R.style.TextAppearance_Material_Body2);
            bt1.setTextSize(TypedValue.COMPLEX_UNIT_SP,ActiveTxt);
            getHistory();

        } else if (n == 2) {
            bt2.setTextAppearance(android.R.style.TextAppearance_Material_Body2);
            bt2.setTextSize(TypedValue.COMPLEX_UNIT_SP,ActiveTxt);
            findViewById(R.id.AddReq).setVisibility(View.VISIBLE);
            findViewById(R.id.form).setVisibility(View.VISIBLE);

        } else if (n== 3) {
            bt3.setTextAppearance(android.R.style.TextAppearance_Material_Body2);
            bt3.setTextSize(TypedValue.COMPLEX_UNIT_SP,ActiveTxt);
            findViewById(R.id.AddTest).setVisibility(View.VISIBLE);
            findViewById(R.id.form2).setVisibility(View.VISIBLE);

        }

    }

    public void OnClickDownload (View v) {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permission is granted");
            Savefile ();
        } else {
            System.out.println("Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        Savefile ();
                    } else {
                        Toast.makeText(this, "Разрешите доступ к хранилищу", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public void Savefile () {
        try {
            String fileName = "example.pdf";
            OutputStream oos;
            String SavedName = null;
            InputStream is = getResources().openRawResource(R.raw.example);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                final Uri contentUri = MediaStore.Files.getContentUri("external");
                Uri fileUri = resolver.insert(contentUri, contentValues);
                oos = resolver.openOutputStream(Objects.requireNonNull(fileUri));
                File f = new File(getRealPathFromURI(this, fileUri));
                SavedName = f.getName();
            } else {
                oos = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + '/' + fileName);
                SavedName = fileName;
            }
            int c = 0;
            byte[] buf = new byte[8192];
            while ((c = is.read(buf, 0, buf.length)) > 0) {
                oos.write(buf, 0, c);
                oos.flush();
            }
            oos.close();
            is.close();
            System.out.println("Wrote to file: " + fileName);
            Toast.makeText(this, "Файл " + SavedName + " загружен.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_LONG).show();
        }



    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void OnAddButton (View v) {
        int n = Integer.parseInt(v.getTag().toString());
        showFileChooser(n);
    }

    public void OnClickButton1 (View v) {
        int n = Integer.parseInt(v.getTag().toString());
        AsyncUploader uploadFileToServer = new AsyncUploader();
        if (n == 1) {
            uploadFileToServer.execute("req");
        } else {
            uploadFileToServer.execute("test");
        }
    }

    public void ShowDatePicker (View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(MainScreen.this,
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

    public void lineUlt  (int n) {
        if (f != null) {
            ((ViewManager)f.getParent()).removeView(f);
        }
        View v = new View(this);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height_f);
        v.setLayoutParams(params);
        v.setBackgroundResource(R.color.black);
        v.setId(R.id.reservedNamedId);
        if (n==1) {
            LinearLayout li = (LinearLayout) findViewById(R.id.layln1);
            li.addView(v);
        } else if (n==2) {
            LinearLayout li = (LinearLayout) findViewById(R.id.layln2);
            li.addView(v);
        } else {
            LinearLayout li = (LinearLayout) findViewById(R.id.layln3);
            li.addView(v);
        }
        f = v;
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

    public String getValue(){
        SharedPreferences preferences = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        return preferences.getString("id", "");
    }


    private class AsyncUploader extends AsyncTask<String, String, String> {
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

            resultProcess (result);
        }

        private String uploadFile(String requestTo) {

            if (inputData.get(requestTo) == null && !(requestTo.equals("jsonReq") || requestTo.equals("jsonTest"))) return "100";
            try {
                OkHttpClient client = new OkHttpClient();
                MultipartBody.Builder buildBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("id", getValue());

                RequestBody requestBody;
                if (requestTo.equals("jsonReq") || requestTo.equals("jsonTest")) {
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inputJson.get(requestTo));
                    requestBody = buildBody.addFormDataPart("file","file.json",body).build();
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
                response.body().close();
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
        } else if (result.equals("100")) {
            Toast.makeText(this,"Необходимо прикрепить файл", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"No internet coonection", Toast.LENGTH_LONG).show();
        }
    }

}