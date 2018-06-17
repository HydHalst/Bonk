package com.ncs.projecteranking.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.ncs.projecteranking.R;
import com.ncs.projecteranking.models.Game;
import com.ncs.projecteranking.models.Ranking;
import com.ncs.projecteranking.models.RankingList;
import com.ncs.projecteranking.models.ResultGame;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class RankingSearchTask extends AsyncTask<String, Integer, RankingList> {

    public interface WeakReference {
        Context getContext();

        ProgressBar getProgressBar();

        void finished(RankingList rankingList);
    }

    private WeakReference weakReference;
    private int idRecieved;

    public RankingSearchTask(WeakReference weakReference, int idRecieved) {
        super();
        this.weakReference = weakReference;
        this.idRecieved = idRecieved;
    }

    @Override
    protected void onPreExecute() {
        Log.d("ncs", "onPreExecute: ");
        ProgressBar progressBar = weakReference.getProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected RankingList doInBackground(String... strings) {

        Log.d("ncs", "doInBackground: ");

        InputStream in = null;

        try {
//        BUILD URL
            String urlStr = String.format(weakReference.getContext().getString(R.string.searchRankingList), idRecieved);
            Log.d("ncs", "URL: " + urlStr);

//            OPEN CONNECTION
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();

            int length = connection.getContentLength();
            Log.d("ncs", "ContentLength: " + length);

//            DOWNLOAD JSON
            in = url.openStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int total = 0, nRead;
            while ((nRead = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, nRead);
                total += nRead;
            }
            String json = new String(outputStream.toByteArray());
            Log.d("ncs", "JSON: " + json);

//            DECODE JSON
            Gson gson = new Gson();
            ResultGame resultGame = gson.fromJson(json, ResultGame.class);
            Log.d("ncs", "RESULT: " + resultGame);

            Game game = resultGame.getGame();
            Log.d("ncs", "GAME: " + game);
            Ranking[] rankings = game.getRanking();
            for (Ranking ranking : rankings) {
                Log.d("ncs", "RANKING: " + ranking);
            }

            RankingList rankingList = new RankingList();
            rankingList.setRankings(rankings);

            Log.d("ncs", "RANKING_LIST: " + rankingList);

            return rankingList;
        } catch (Exception e) {
            Log.e("ncs", e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(RankingList rankingList) {
        Log.d("ncs", "onPostExecute: ");
        ProgressBar progressBar = weakReference.getProgressBar();
        progressBar.setVisibility(View.GONE);
        weakReference.finished(rankingList);
    }
}
