package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CSFileRowAdapter extends RecyclerView.Adapter<CSFileRowAdapter.Holder>
{
    public ArrayList<CSFilePOJO> arrayList;
    public Object systemService;
    public OnFileClickListener listener;

    public CSFileRowAdapter(ArrayList<CSFilePOJO> arrayList,Object systemService, OnFileClickListener listener) {
        this.arrayList = arrayList;
        this.systemService = systemService;
        this.listener = listener;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cs_row,parent,false);
        return new CSFileRowAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.btn_download.setOnClickListener(view -> {
            downloadFile(arrayList.get(position).getFile_url());
        });

        holder.txt_file_name.setText(arrayList.get(position).getFile_name());
        holder.bind(arrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder
    {

        TextView txt_file_name;
        ImageButton btn_download;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txt_file_name = itemView.findViewById(R.id.txt_file_name);
            btn_download = itemView.findViewById(R.id.btn_download);

        }


        public void bind(final CSFilePOJO item, final OnFileClickListener listener) {
            itemView.setOnLongClickListener(view -> {
                listener.onFileClicked(item);
                return true;
            });

        }

    }

    public void downloadFile(String url)
    {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        String title = URLUtil.guessFileName(url,null,null);
        request.setTitle(title);
        request.setDescription("Downloading file please wait...");
        String cookie = CookieManager.getInstance().getCookie(url);
        request.addRequestHeader("cookie",cookie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);

        DownloadManager downloadManager = (DownloadManager) systemService;

        downloadManager.enqueue(request);
        Toast.makeText(ActivityCSFile.ActivityCSFileContext, "Download started", Toast.LENGTH_SHORT).show();
    }

}
