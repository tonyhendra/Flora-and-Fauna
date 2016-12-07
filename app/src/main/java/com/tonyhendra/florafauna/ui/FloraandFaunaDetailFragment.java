
package com.tonyhendra.florafauna.ui;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.tonyhendra.florafauna.presenters.FloraandFaunaDetailPresenter;
import com.tonyhendra.florafauna.R;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.behaviors.ShareBehavior;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.image.ImageLoader;
import ibmmobileappbuilder.util.image.PicassoImageLoader;
import ibmmobileappbuilder.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import static ibmmobileappbuilder.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;
import ibmmobileappbuilder.analytics.AnalyticsReporter;
import static ibmmobileappbuilder.analytics.model.AnalyticsInfo.Builder.analyticsInfo;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.tonyhendra.florafauna.ds.DataflorafaunaDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class FloraandFaunaDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<DataflorafaunaDSSchemaItem> implements ShareBehavior.ShareListener  {
    private String textSpeech;
    StreamPlayer streamPlayer;
    ProgressDialog pDialog;
    private CrudDatasource<DataflorafaunaDSSchemaItem> datasource;
    private AnalyticsReporter analyticsReporter;


    public static FloraandFaunaDetailFragment newInstance(Bundle args){
        FloraandFaunaDetailFragment fr = new FloraandFaunaDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public FloraandFaunaDetailFragment(){
        super();
    }


    @Override
    public Datasource<DataflorafaunaDSSchemaItem> getDatasource() {
        if (datasource != null) {
            return datasource;
    }
       datasource = CloudantDatasource.cloudantDatasource(
              CloudantDatastoresFactory.create("data_flora_fauna"),
              URI.create("https://1ae7e3be-a478-4387-afcc-434e53aeb810-bluemix:716244766aba7e12cd8302b3263bf4fa939ec97f235c5de06dc0eeff3b2abcac@1ae7e3be-a478-4387-afcc-434e53aeb810-bluemix.cloudant.com/data_flora_fauna"),
              DataflorafaunaDSSchemaItem.class,
              new SearchOptions()
              );
        return datasource;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        addBehavior(pageViewBehavior("FloraandFaunaDetail"));
        analyticsReporter = AnalyticsReporterInjector.analyticsReporter(getActivity());
        // the presenter for this view
        setPresenter(new FloraandFaunaDetailPresenter(
                (CrudDatasource) getDatasource(),
                this));
        // Edit button


        addBehavior(new FabBehaviour(this, R.drawable.play, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((DetailCrudPresenter<DataflorafaunaDSSchemaItem>) getPresenter()).editForm(getItem());
                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});

            }
        }));
        addBehavior(new ShareBehavior(getActivity(), this));



    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.floraandfaunadetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final DataflorafaunaDSSchemaItem item, View view) {
        
        ImageView view0 = (ImageView) view.findViewById(R.id.view0);
        URL view0Media = StringUtils.parseUrl(item.image);
        if(view0Media != null){
            ImageLoader imageLoader = new PicassoImageLoader(view0.getContext());
            imageLoader.load(imageLoaderRequest()
                                   .withPath(view0Media.toExternalForm())
                                   .withTargetView(view0)
                                   .fit()
                                   .build()
                    );
            
        } else {
            view0.setImageDrawable(null);
        }
        if (item.name != null){
            
            TextView view1 = (TextView) view.findViewById(R.id.view1);
            view1.setText(item.name);
            
        }
        if (item.scientific_name != null){
            
            TextView view2 = (TextView) view.findViewById(R.id.view2);
            view2.setText(item.scientific_name);
            
        }
        if (item.lifespan != null){
            
            TextView view3 = (TextView) view.findViewById(R.id.view3);
            view3.setText(item.lifespan);
            
        }
        if (item.rank != null){
            
            TextView view4 = (TextView) view.findViewById(R.id.view4);
            view4.setText(item.rank);
            
        }
        if (item.higher_classification != null){
            
            TextView view5 = (TextView) view.findViewById(R.id.view5);
            view5.setText(item.higher_classification);
            
        }
        if (item.height != null){
            
            TextView view6 = (TextView) view.findViewById(R.id.view6);
            view6.setText(item.height);
            
        }
        if (item.mass != null){
            
            TextView view7 = (TextView) view.findViewById(R.id.view7);
            view7.setText(item.mass);
            
        }
        if(item.speech !=null){
            textSpeech = item.speech;

        }
    }

    @Override
    protected void onShow(DataflorafaunaDSSchemaItem item) {
        // set the title for this fragment
        getActivity().setTitle(item.name);
    }

    @Override
    public void onShare() {
        DataflorafaunaDSSchemaItem item = getItem();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, (item.name != null ? item.name : "" ) + "\n" +
                    (item.scientific_name != null ? item.scientific_name : "" ) + "\n" +
                    (item.lifespan != null ? item.lifespan : "" ) + "\n" +
                    (item.rank != null ? item.rank : "" ) + "\n" +
                    (item.higher_classification != null ? item.higher_classification : "" ) + "\n" +
                    (item.height != null ? item.height : "" ) + "\n" +
                    (item.mass != null ? item.mass : "" ));
        intent.putExtra(Intent.EXTRA_SUBJECT, item.name);
        analyticsReporter.sendEvent(analyticsInfo()
                            .withAction("share")
                            .withTarget((item.name != null ? item.name : "" ) + "\n" +
                    (item.scientific_name != null ? item.scientific_name : "" ) + "\n" +
                    (item.lifespan != null ? item.lifespan : "" ) + "\n" +
                    (item.rank != null ? item.rank : "" ) + "\n" +
                    (item.higher_classification != null ? item.higher_classification : "" ) + "\n" +
                    (item.height != null ? item.height : "" ) + "\n" +
                    (item.mass != null ? item.mass : "" ))
                            .withDataSource("DataflorafaunaDS")
                            .build().toMap()
        );
        startActivityForResult(Intent.createChooser(intent, getString(R.string.share)), 1);
    }
    private TextToSpeech initTextToSpeechService(){
        TextToSpeech service = new TextToSpeech();
        String username = "fae19735-8ef1-4018-a052-2291d5fdcaa3";
        String password = "n0ySdQf1R2Xu";
        service.setUsernameAndPassword(username, password);
        return service;
    }


    private class WatsonTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Audio is playing, wait until this done!");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... textToSpeak) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    //textView.setText("running the Watson thread");
//                }
//            });
            TextToSpeech textToSpeech = initTextToSpeechService();
            streamPlayer = new StreamPlayer();
            streamPlayer.playStream(textToSpeech.synthesize(String.valueOf(textSpeech),Voice.EN_LISA).execute());
//            streamPlayer


            return "text to speech done";
        }

        @Override
        protected void onPostExecute(String result) {
//            textView.setText("TTS status: " + result);]
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

    }

}
