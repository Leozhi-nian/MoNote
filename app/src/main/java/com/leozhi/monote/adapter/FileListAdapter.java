package com.leozhi.monote.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.leozhi.monote.bean.FileBean;
import com.leozhi.monote.databinding.LayoutFileBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author leozhi
 * @date 20-9-16
 */
public class FileListAdapter extends ListAdapter<FileBean, FileListAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private LayoutFileBinding mBinding;

    public FileListAdapter() {
        super(new DiffUtil.ItemCallback<FileBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull FileBean oldItem, @NonNull FileBean newItem) {
                // 此处修改为Uri
                return oldItem.getFileUri() == newItem.getFileUri();
            }

            @Override
            public boolean areContentsTheSame(@NonNull FileBean oldItem, @NonNull FileBean newItem) {
                return oldItem.getFileName().equals(newItem.getFileName());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = LayoutFileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setBinding(getItem(position));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutFileBinding binding;

        public ViewHolder(LayoutFileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setBinding(FileBean data) {
            SimpleDateFormat format = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " , Locale.SIMPLIFIED_CHINESE);
            binding.fileName.setText(data.getFileName());
            binding.fileSize.setText(data.getFileSize());
            binding.fileDate.setText(format.format(data.getFileDate()));
        }
    }
}
