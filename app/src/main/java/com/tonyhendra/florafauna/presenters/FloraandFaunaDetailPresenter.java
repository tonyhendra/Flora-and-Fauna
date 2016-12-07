
package com.tonyhendra.florafauna.presenters;

import com.tonyhendra.florafauna.R;
import com.tonyhendra.florafauna.ds.DataflorafaunaDSSchemaItem;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.mvp.view.DetailView;
import java.util.Map;
import java.util.HashMap;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;

public class FloraandFaunaDetailPresenter extends BasePresenter implements DetailCrudPresenter<DataflorafaunaDSSchemaItem>,
      Datasource.Listener<DataflorafaunaDSSchemaItem> {

    private final CrudDatasource<DataflorafaunaDSSchemaItem> datasource;
    private final DetailView view;

    public FloraandFaunaDetailPresenter(CrudDatasource<DataflorafaunaDSSchemaItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(DataflorafaunaDSSchemaItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(DataflorafaunaDSSchemaItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(DataflorafaunaDSSchemaItem item) {
        logCrudAction("deleted", item.getIdentifiableId());
        view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
    }
    private void logCrudAction(String action, String id) {
      Map<String, String> paramsMap = new HashMap<>(3);
      //action will be one of created, updated or deleted
      paramsMap.put("action", action);
      paramsMap.put("entity", "DataflorafaunaDSSchemaItem");
      paramsMap.put("identifier", id);

      AnalyticsReporterInjector.analyticsReporter().sendEvent(paramsMap);
    }
}
