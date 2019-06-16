package com.bdjobs.currencycalculator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static double c,r,t;
    static double reducedRate,superReducedRate,standardRate;
    private Spinner countryList;
    private EditText currencyAmount;
    private TextView totalAmt;
    private RadioGroup rates;
    private RadioButton reduced,superReduced,standard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryList=findViewById(R.id.countryListSp);
        currencyAmount=findViewById(R.id.currencyAmountEt);
        totalAmt=findViewById(R.id.totalAmtTv);
        rates=findViewById(R.id.vatRateRg);
        rates.check(R.id.reducedRateRb);
        reduced=findViewById(R.id.reducedRateRb);
        superReduced=findViewById(R.id.superReducedRateRb);
        standard=findViewById(R.id.standardRateRb);

        fetchAllData();

        currencyAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton b=findViewById(checkedId);
                    String sb=b.getText().toString();
                if (sb.equals("Reduced")){
                    r=10;
                }else if(sb.equals("Super Reduced")){
                    r=4;
                }else{
                    r=21;
                }
                setTotal();
                //Toast.makeText(MainActivity.this,b.getText().toString(),Toast.LENGTH_SHORT).show();
          }
        });

    }

    private void setTotal(){
        c=Double.valueOf("0"+currencyAmount.getText().toString());
         RadioButton id=findViewById(rates.getCheckedRadioButtonId());
        String v=id.getText().toString();
        if (v.equals("Reduced")){
            r=10;
        }else if(v.equals("Super Reduced")){
            r=4;
        }else{
            r=21;
        }
        t=c+(Double.valueOf("0"+currencyAmount.getText().toString())*(r))/100;
        totalAmt.setText("Total =                 "+t);
    }




    //===============================================
    private MainActivity getActivity() {
        return this;
    }

    private void fetchAllData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "https://jsonvat.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("stringList", "ok");
                //hideProgress();
                try {
                    JSONObject mobj = new JSONObject(response);
                    Log.d("jsonvat",response);
                    //final JSONArray samityArray = mobj.getJSONArray("samitys");
                    //final JSONArray memberArray = mobj.getJSONArray("members");
                    //final JSONArray collectionArray = mobj.getJSONArray("collections");
                    //Toast.makeText(this, "Download Completd", Toast.LENGTH_LONG).show();
                    //Log.d("samitydata", samityArray.toString());
                    //Log.d("memberdata", memberArray.toString());
                    //Log.d("collectiondata", collectionArray.toString());
                    final JsonVat jsonVat = new JsonVat();
                    try {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {/*
                                DbInfo.getDatabase(getActivity()).memberInfoDao().deleteAllMember();
                                DbInfo.getDatabase(getActivity()).collectionSheetDao().deleteAll();

                                DbInfo db = DbInfo.getDatabase(getActivity());
                                try {
                                    for (int i = 0; i < samityArray.length(); i++) {
                                        JSONObject object = samityArray.getJSONObject(i);
                                        //Log.d("samitydata", object.getString("samityCode"));
                                        //Log.d("samitydata", object.getString("samityName"));
                                        jsonVat.setSamityCode(object.getString("samityCode"));
                                        jsonVat.setSamityName(object.getString("samityName"));
                                        jsonVat.setStatus(object.getString("samityStatus"));
                                    }
                                    for (int i = 0; i < memberArray.length(); i++) {
                                        JSONObject obj = memberArray.getJSONObject(i);
                                        //Log.d("Loop:", i + "");
                                        MemberInfo memberInfo = new MemberInfo();
                                        MemberId memberId = new MemberId();
                                        memberId.setMemberId(obj.getString("memberId"));
                                        memberId.setSamityCode(obj.getString("samityCode"));
                                        memberInfo.setMemberId(memberId);
                                        memberInfo.setMemberName(obj.getString("memberName"));
                                        memberInfo.setFatherName(obj.getString("fatherName"));
                                        memberInfo.setHusbandName(obj.getString("husbandName"));
                                        memberInfo.setSex(obj.getString("sex"));
                                        memberInfo.setStatus(obj.getString("status"));
                                        Log.d("Row", memberInfo.toString());
                                        db.memberInfoDao().insert(memberInfo);
                                    }
                                    CollectionSheetId collectionSheetId;
                                    JSONObject obj;
                                    CollectionSheet collectionSheet;
                                    for (int i = 0; i < collectionArray.length(); i++) {
                                        obj = collectionArray.getJSONObject(i);
                                        Log.d("lobj", obj.toString());
                                        Log.d("lobj", collectionArray.length() + "");

                                        collectionSheetId = new CollectionSheetId();
                                        if (obj.has("companyCode")) {
                                            collectionSheetId.setCompanyCode(obj.getString("companyCode"));
                                        }
                                        if (obj.has("companyBranchCode")) {
                                            collectionSheetId.setCompanyBranchCode(obj.getString("companyBranchCode"));
                                        }
                                        if (obj.has("samityCode")) {
                                            collectionSheetId.setSamityCode(obj.getString("samityCode"));
                                        }
                                        if (obj.has("memberId")) {
                                            collectionSheetId.setMemberId(obj.getString("memberId"));
                                        }
                                        if (obj.has("collectionDate")) {
                                            collectionSheetId.setCollectDate(obj.getString("collectionDate"));
                                        }
                                        if (obj.has("slNo")) {
                                            collectionSheetId.setSlNo(obj.getInt("slNo"));
                                        }
                                        if (obj.has("financeCode")) {
                                            collectionSheetId.setFinanceCode("financeCode");
                                        }
                                        if (obj.has("projectCode")) {
                                            collectionSheetId.setProjectCode("projectCode");
                                        }
                                        if (obj.has("componentCode")) {
                                            collectionSheetId.setComponentCode("componentCode");
                                        }
                                        collectionSheet = new CollectionSheet();
                                        collectionSheet.setCollectionSheetId(collectionSheetId);
                                        if (obj.has("loanCode")) {
                                            collectionSheet.setLoanCode(obj.getString("loanCode"));
                                        }
                                        if (obj.has("loanName")) {
                                            collectionSheet.setLoanName(obj.getString("loanName"));
                                        }
                                        if (obj.has("mnyr")) {
                                            collectionSheet.setMnyr(obj.getString("mnyr"));
                                        }
                                        if (obj.has("wkyr")) {
                                            collectionSheet.setWkyr(obj.getString("wkyr"));
                                        }

                                        if (obj.has("attendance")) {
                                            collectionSheet.setAttendance(obj.getString("attendance"));
                                        }
                                        if (obj.has("dafaNo")) {
                                            collectionSheet.setDafaNo(obj.getInt("dafaNo"));
                                        }
                                        if (obj.has("installNo")) {
                                            collectionSheet.setInstallNo(obj.getInt("installNo"));
                                        }
                                        if (obj.has("ttlInstallAmtRcvd")) {
                                            collectionSheet.setTtlInstallAmtRcvd(obj.getDouble("ttlInstallAmtRcvd"));
                                        }
                                        if (obj.has("totalServiceCharge")) {
                                            collectionSheet.setTotalServiceCharge(obj.getDouble("totalServiceCharge"));
                                        }
                                        if (obj.has("openingLoanOutstanding")) {
                                            collectionSheet.setOpeningLoanOutstanding(obj.getDouble("openingLoanOutstanding"));
                                        }
                                        if (obj.has("prnLoanOutstanding")) {
                                            collectionSheet.setPrnLoanOutstanding(obj.getDouble("prnLoanOutstanding"));
                                        }
                                        if (obj.has("scLoanOutstanding")) {
                                            collectionSheet.setScLoanOutstanding(obj.getDouble("scLoanOutstanding"));
                                        }
                                        if (obj.has("installType")) {
                                            collectionSheet.setInstallType(obj.getString("installType"));
                                        }
                                        if (obj.has("saveVolRcvd")) {
                                            collectionSheet.setSaveVolRcvd(obj.getDouble("saveVolRcvd"));
                                        }
                                        if (obj.has("saveOtherRcvd")) {
                                            collectionSheet.setSaveOtherRcvd(obj.getDouble("saveOtherRcvd"));
                                        }
                                        if (obj.has("postingFlag")) {
                                            collectionSheet.setMisPostingFlag(obj.getString("postingFlag"));
                                        }
                                        if (obj.has("ttlInstallPrnRcvd")) {
                                            collectionSheet.setTtlInstallPrnRcvd(obj.getDouble("ttlInstallPrnRcvd"));
                                        }
                                        if (obj.has("ttlInstallScRcvd")) {
                                            collectionSheet.setTtlInstallScRcvd(obj.getDouble("ttlInstallScRcvd"));
                                        }
                                        if (obj.has("disburseAmount")) {
                                            collectionSheet.setDisburseAmount(obj.getDouble("disburseAmount"));
                                        }
                                        if (obj.has("disburseDate")) {
                                            collectionSheet.setDisburseDate(obj.getString("disburseDate"));
                                        }
                                        if (obj.has("loanRecievale")) {
                                            collectionSheet.setLoanRecivable(obj.getDouble("loanRecievale"));
                                        }
                                        if (obj.has("loanPrnRecievale")) {
                                            collectionSheet.setLoanPrnRecievable(obj.getDouble("loanPrnRecievale"));
                                        }
                                        if (obj.has("loanScRecievale")) {
                                            collectionSheet.setLoanScRecievable(obj.getDouble("loanScRecievale"));
                                        }
                                        if (obj.has("collectionDate")) {
                                            collectionSheet.setCollectionDate(obj.getString("collectionDate"));
                                        }

                                        //======01-04-2019=====
                                        if (obj.has("amountRebate")) {
                                            collectionSheet.setAmountRebate(obj.getDouble("amountRebate"));
                                        }
                                        if (obj.has("saveCompRcvd")) {
                                            collectionSheet.setSaveCompCode(obj.getDouble("saveCompRcvd"));
                                        }
                                        if (obj.has("insWfRcvd")) {
                                            collectionSheet.setInsWfCode(obj.getDouble("insWfRcvd"));
                                        }
                                        if (obj.has("purposeDesc")) {
                                            collectionSheet.setLoanPurposeDesc(obj.getString("purposeDesc"));
                                        }
                                        if (obj.has("installStartDate")) {
                                            collectionSheet.setInstallStartDate(obj.getString("installStartDate"));
                                        }
                                        if (obj.has("openingLoanOverdue")) {
                                            collectionSheet.setOpeningLoanOverdue(obj.getDouble("openingLoanOverdue"));
                                        }
                                        if (obj.has("openingPrnLoanOverdue")) {
                                            collectionSheet.setOpeningPrnLoanOverdue(obj.getDouble("openingPrnLoanOverdue"));
                                        }
                                        if (obj.has("openingScLoanOverdue")) {
                                            collectionSheet.setOpeningScLoanOverdue(obj.getDouble("openingScLoanOverdue"));
                                        }
                                        if (obj.has("openingOverdueDate")) {
                                            collectionSheet.setOpeningOverdueDate(obj.getString("openingOverdueDate"));
                                        }
                                        if (obj.has("openingLoanAdvance")) {
                                            collectionSheet.setOpeningLoanAdvance(obj.getDouble("openingLoanAdvance"));
                                        }
                                        if (obj.has("saveCompOutstanding")) {
                                            collectionSheet.setSaveCompOutstanding(obj.getDouble("saveCompOutstanding"));
                                        }
                                        if (obj.has("saveCompOverdue")) {
                                            collectionSheet.setSaveCompOverdue(obj.getDouble("saveCompOverdue"));
                                        }
                                        if (obj.has("saveVolOutstanding")) {
                                            collectionSheet.setSaveVolOutstanding(obj.getDouble("saveVolOutstanding"));
                                        }
                                        if (obj.has("saveOtherOutstanding")) {
                                            collectionSheet.setSaveOtherOutstanding(obj.getDouble("saveOtherOutstanding"));
                                        }
                                        if (obj.has("loanScRecievale")) {
                                            collectionSheet.setLoanScRecievable(obj.getDouble("loanScRecievale"));
                                        }
                                        if (obj.has("loanRcvdIrregular")) {
                                            collectionSheet.setLoanRcvdIrregular(obj.getDouble("loanRcvdIrregular"));
                                        }
                                        if (obj.has("saveCompIrregular")) {
                                            collectionSheet.setSaveCompIrregular(obj.getDouble("saveCompIrregular"));
                                        }
                                        if (obj.has("saveVolIrregular")) {
                                            collectionSheet.setSaveVolIrregular(obj.getDouble("saveVolIrregular"));
                                        }
                                        // LOAN CLOSING
                                        if (obj.has("loanReceivedRegular")) {
                                            collectionSheet.setLoanReceivedRegular(obj.getDouble("loanReceivedRegular"));
                                        }
                                        if (obj.has("loanReceivedOverdue")) {
                                            collectionSheet.setLoanReceivedOverdue(obj.getDouble("loanReceivedOverdue"));
                                        }
                                        if (obj.has("loanReceivedAdvance")) {
                                            collectionSheet.setLoanReceivedAdvance(obj.getDouble("loanReceivedAdvance"));
                                        }
                                        if (obj.has("loanAdjAdvance")) {
                                            collectionSheet.setLoanAdjAdvance(obj.getDouble("loanAdjAdvance"));
                                        }
                                        if (obj.has("loanOustanding")) {
                                            collectionSheet.setLoanOustanding(obj.getDouble("loanOustanding"));
                                        }
                                        if (obj.has("loanOverdue")) {
                                            collectionSheet.setLoanOverdue(obj.getDouble("loanOverdue"));
                                        }
                                        if (obj.has("loanOverdueDate")) {
                                            collectionSheet.setLoanOverdueDate(obj.getString("loanOverdueDate"));
                                        }
                                        if (obj.has("loanAdvance")) {
                                            collectionSheet.setLoanAdvance(obj.getDouble("loanAdvance"));
                                        }
                                        if (obj.has("loanOverdueWeekly")) {
                                            collectionSheet.setLoanOverdueWeekly(obj.getDouble("loanOverdueWeekly"));
                                        }
                                        if (obj.has("saveIntAccrued")) {
                                            collectionSheet.setSaveIntAccrued(obj.getDouble("saveIntAccrued"));
                                        }
                                        if (obj.has("saveOutCompCls")) {
                                            collectionSheet.setSaveOutCompCls(obj.getDouble("saveOutCompCls"));
                                        }
                                        if (obj.has("saveOutVolCls")) {
                                            collectionSheet.setSaveOutVolCls(obj.getDouble("saveOutVolCls"));
                                        }
                                        if (obj.has("saveOutOtherCls")) {
                                            collectionSheet.setSaveOutOtherCls(obj.getDouble("saveOutOtherCls"));
                                        }
                                        //================
                                        System.out.println(collectionSheet);
                                        db.collectionSheetDao().insert(collectionSheet);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("stringList", "Error");
                Toast.makeText(getActivity(), "Server Error!", Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
               /* Log.d("sssm", companyCode + companyBranchCode + empId);

                params.put("companyCode", companyCode);
                params.put("companyBranchCode", companyBranchCode);
                params.put("empId", empId);
                //
                params.put("financeCode", financeCode);
                params.put("projectCode", financeCode);
                params.put("componentCode", componentCode);*/
                //Log.d("pvalue",companyCode+companyBranchCode+empId+financeCode+financeCode+componentCode);
                return params;
            }
        };
        queue.add(request);
    }






}
