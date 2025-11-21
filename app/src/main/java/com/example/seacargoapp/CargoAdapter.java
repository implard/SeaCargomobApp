package com.example.seacargoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.seacargoapp.R;
import com.example.seacargoapp.models.Cargo;
import java.util.ArrayList;
import java.util.List;

public class CargoAdapter extends RecyclerView.Adapter<CargoAdapter.CargoViewHolder> {

    private List<Cargo> cargoList = new ArrayList<>();

    @NonNull
    @Override
    public CargoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cargo, parent, false);
        return new CargoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CargoViewHolder holder, int position) {
        Cargo cargo = cargoList.get(position);
        holder.tvCargoName.setText(cargo.getName());
        holder.tvWeight.setText("Вес: " + cargo.getWeight());
        holder.tvRoute.setText(cargo.getSender() + " → " + cargo.getReceiver());
        holder.tvStatus.setText(cargo.getStatus());
    }

    @Override
    public int getItemCount() {
        return cargoList.size();
    }

    public void updateData(List<Cargo> newList) {
        cargoList.clear();
        if (newList != null) {
            cargoList.addAll(newList);
        }
        notifyDataSetChanged();
    }

    static class CargoViewHolder extends RecyclerView.ViewHolder {
        TextView tvCargoName, tvWeight, tvRoute, tvStatus;

        public CargoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCargoName = itemView.findViewById(R.id.tvCargoName);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}