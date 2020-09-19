package com.leozhi.monote.adapter;

import android.view.LayoutInflater;
import android.view.View;
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
public class FileListAdapter extends ListAdapter<FileBean, FileListAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public FileListAdapter() {
        super(new DiffUtil.ItemCallback<FileBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull FileBean oldItem, @NonNull FileBean newItem) {
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
        LayoutFileBinding mBinding = LayoutFileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        mBinding.getRoot().setOnClickListener(this);
        mBinding.getRoot().setOnLongClickListener(this);
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

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(recyclerView, v, position, getItem(position));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(recyclerView, v, position, getItem(position));
        }
        return true;
    }

    /**
     * 文件列表项目点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position, FileBean file);
    }

    /**
     * 文件列表项目长按事件
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(RecyclerView parent, View view, int position, FileBean file);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
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
