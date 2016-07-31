package np.com.sagardevkota.lighthttpexample.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import np.com.sagardevkota.lighthttp.LightHTTP;
import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.utils.CacheManager;
import np.com.sagardevkota.lighthttpexample.R;
import np.com.sagardevkota.lighthttpexample.models.User;


/**
 * Created by megha on 15-03-06.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> implements View.OnClickListener {

    Context mContext;
    List<User> items;
    CacheManager<Bitmap> bitmapCacheManager;
    public UserListAdapter(Context context) {
        this.mContext = context;
        this.bitmapCacheManager= new CacheManager<>(40*1024*1024);

    }

    public void setItems(List<User> items){
        this.items=items;
    }


    public void clear(){
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        User user=items.get(position);
        if(user==null) return;
        holder.txtName.setText(user.getName());

        LightHTTP
                .from(mContext)
                .load(LightHTTP.Method.GET, user.getImageUrl())
                .asBitmap()
                .setCacheManager(bitmapCacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
                        holder.progLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            holder.imgUser.setImageBitmap(data);
                            holder.progLoading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        holder.progLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        holder.progLoading.setVisibility(View.GONE);
                    }
                });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgUser;
        public TextView txtName;
        public ProgressBar progLoading;

        public ViewHolder(View itemView) {
            super(itemView);
            imgUser = (ImageView) itemView.findViewById(R.id.imgUser);
            txtName=(TextView) itemView.findViewById(R.id.txtName);
            progLoading=(ProgressBar) itemView.findViewById(R.id.progressImageLoad);
            progLoading.setVisibility(View.GONE);

        }
    }







}
