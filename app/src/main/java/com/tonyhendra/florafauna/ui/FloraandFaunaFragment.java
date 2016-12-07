package com.tonyhendra.florafauna.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tonyhendra.florafauna.R;
import ibmmobileappbuilder.behaviors.SearchBehavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ui.ListGridFragment;
import ibmmobileappbuilder.util.image.ImageLoader;
import ibmmobileappbuilder.util.image.PicassoImageLoader;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.util.ViewHolder;
import java.net.URL;
import static ibmmobileappbuilder.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.tonyhendra.florafauna.ds.DataflorafaunaDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;
import android.content.Intent;
import ibmmobileappbuilder.util.Constants;
import static ibmmobileappbuilder.util.NavigationUtils.generateIntentToAddOrUpdateItem;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

/**
 * "FloraandFaunaFragment" listing
 */
public class FloraandFaunaFragment extends ListGridFragment<DataflorafaunaDSSchemaItem>  {

    private Datasource<DataflorafaunaDSSchemaItem> datasource;


    public static FloraandFaunaFragment newInstance(Bundle args) {
        FloraandFaunaFragment fr = new FloraandFaunaFragment();

        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBehavior(pageViewBehavior("FloraandFauna"));
        addBehavior(new SearchBehavior(this));
        
    }

    protected SearchOptions getSearchOptions() {
        SearchOptions.Builder searchOptionsBuilder = SearchOptions.Builder.searchOptions();
        return searchOptionsBuilder.build();
    }


    /**
    * Layout for the list itselft
    */
    @Override
    protected int getLayout() {
        return R.layout.fragment_grid;
    }

    /**
    * Layout for each element in the list
    */
    @Override
    protected int getItemLayout() {
        return R.layout.floraandfauna_item;
    }

    @Override
    protected Datasource<DataflorafaunaDSSchemaItem> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
       datasource = CloudantDatasource.cloudantDatasource(
              CloudantDatastoresFactory.create("data_flora_fauna"),
              URI.create("https://1ae7e3be-a478-4387-afcc-434e53aeb810-bluemix:716244766aba7e12cd8302b3263bf4fa939ec97f235c5de06dc0eeff3b2abcac@1ae7e3be-a478-4387-afcc-434e53aeb810-bluemix.cloudant.com/data_flora_fauna"),
              DataflorafaunaDSSchemaItem.class,
              getSearchOptions()
              );
        return datasource;
    }

    @Override
    protected void bindView(DataflorafaunaDSSchemaItem item, View view, int position) {
        
        ImageLoader imageLoader = new PicassoImageLoader(view.getContext());
        ImageView image = ViewHolder.get(view, R.id.image);
        URL imageMedia = StringUtils.parseUrl(item.image);
        if(imageMedia != null){
            imageLoader.load(imageLoaderRequest()
                          .withPath(imageMedia.toExternalForm())
                          .withTargetView(image)
                          .fit()
                          .build()
            );
        	
        }
        else {
            imageLoader.load(imageLoaderRequest()
                          .withResourceToLoad(R.drawable.ic_ibm_placeholder)
                          .withTargetView(image)
                          .build()
            );
        }
        
        
        TextView title = ViewHolder.get(view, R.id.title);
        
        if (item.name != null){
            title.setText(item.name);
            
        }
    }


    @Override
    public void showDetail(DataflorafaunaDSSchemaItem item, int position) {
        // If we have forms, then we have to refresh when an item has been edited
        // Also with this we support list without details
        Bundle args = new Bundle();
        args.putInt(Constants.ITEMPOS, position);
        args.putParcelable(Constants.CONTENT, item);
        Intent intent = new Intent(getActivity(), FloraandFaunaDetailActivity.class);
        intent.putExtras(args);

        if (!getResources().getBoolean(R.bool.tabletLayout)) {
            startActivityForResult(intent, Constants.DETAIL);
        } else {
            startActivity(intent);
        }
    }

}
