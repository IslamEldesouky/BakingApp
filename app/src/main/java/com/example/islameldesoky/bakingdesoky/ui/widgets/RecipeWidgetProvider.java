package com.example.islameldesoky.bakingdesoky.ui.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.islameldesoky.bakingdesoky.R;
import com.example.islameldesoky.bakingdesoky.businesslogic.Recipe;
import com.example.islameldesoky.bakingdesoky.ui.recipedetails.RecipeDetailActivity;
import com.example.islameldesoky.bakingdesoky.ui.recipedetails.RecipeDetailFragment;
import com.example.islameldesoky.bakingdesoky.utils.App;

/**
 * Created by islam eldesoky on 17/07/2017.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_TOAST = "com.bakingDesoky.ui.widgets.ACTION_TOAST";
    private int position;
    private Recipe recipe;

    public RecipeWidgetProvider() {
        super();


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_TOAST)) {
            intent = new Intent(context, RecipeDetailActivity.class);
        }
    }
        @Override
        public void onUpdate (Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds)
        {
            super.onUpdate(context, appWidgetManager, appWidgetIds);

            recipe = App.getInstance().getRecipes().get(position);

            for (int widgetId : appWidgetIds) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list);
                //views.setTextViewText(R.id.widget_recipe ,String.valueOf(recipe.getName()));
                Intent intent = new Intent(context, RecipeWidgetService.class);
                views.setRemoteAdapter(
                        R.id.widget_list_view,
                        intent);
                intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

                final Intent OnItemClickintent = new Intent(context, RecipeDetailFragment.class);
                OnItemClickintent.putExtra(RecipeDetailFragment.ARG_POSITION, recipe.getIngredients().get(position).getIngredient());
                OnItemClickintent.setAction(ACTION_TOAST);
                OnItemClickintent.setData(Uri.parse(OnItemClickintent
                        .toUri(Intent.URI_INTENT_SCHEME)));
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, OnItemClickintent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.widget_recipe, pendingIntent);
                views.setPendingIntentTemplate(R.id.widget_list_view,
                        pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, views);
            }
        }

        @Override
        public void onEnabled (Context context){
            super.onEnabled(context);

        }

        @Override
        public void onDisabled (Context context){
            super.onDisabled(context);
        }
    }
