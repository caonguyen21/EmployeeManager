package com.example.employeemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.employeemanager.R;
import com.example.employeemanager.function.GetNhanVien;
import com.example.employeemanager.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder> {

    private Context context;
    private List<NhanVien> mlistNhanVien;
    private OnSwipeListener onSwipeListener;

    @SuppressLint("NotifyDataSetChanged")
    public NhanVienAdapter(Context context, List<NhanVien> mlistNhanVien, OnSwipeListener onswipeListener) {
        this.context = context;
        this.mlistNhanVien = mlistNhanVien;
        this.onSwipeListener = onswipeListener;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien, parent, false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = mlistNhanVien.get(position);
        if (nhanVien != null) {
            holder.txtTenNV.setText(nhanVien.getHoTen());
            holder.txtChucVu.setText(nhanVien.getChucVu());
            holder.txtPhongBan.setText(nhanVien.getPhongBan());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            holder.txtNgayCapNhat.setText(simpleDateFormat.format(nhanVien.getNgayCapNhat()));
            String image = nhanVien.getHinhAnh();
            Glide.with(context)
                    .load(image)
                    .placeholder(R.drawable.baseline_image_24)
                    .into(holder.hinhAnh);
            holder.cvNhanVien.setOnClickListener(v -> {
                //Click vào chi tiết nhân viên
                Intent intent = new Intent(context, GetNhanVien.class);
                intent.putExtra("clickNV", nhanVien);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mlistNhanVien != null)
            return mlistNhanVien.size();
        return 0;
    }

    public interface OnSwipeListener {
        void onLeftSwipe(int position);

        void onRightSwipe(int position);
    }

    public static class NhanVienViewHolder extends RecyclerView.ViewHolder {
        private final CardView cvNhanVien;
        private final TextView txtTenNV, txtChucVu, txtPhongBan, txtNgayCapNhat;

        ImageView hinhAnh;

        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            cvNhanVien = itemView.findViewById(R.id.cvNhanVien);
            txtTenNV = itemView.findViewById(R.id.textHoTen);
            txtChucVu = itemView.findViewById(R.id.textChucVu);
            txtPhongBan = itemView.findViewById(R.id.textPhongBan);
            txtNgayCapNhat = itemView.findViewById(R.id.textUpdateDate);
            hinhAnh = itemView.findViewById(R.id.imageNhanVien);
        }
    }
}

