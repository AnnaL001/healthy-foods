package com.anna.healthyfoods.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.databinding.FragmentStarredBinding;
import com.anna.healthyfoods.utility.UserInterfaceHelpers;

public class StarredFragment extends Fragment {
  private FragmentStarredBinding binding;

  public StarredFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentStarredBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //For now, until database connection is added
    UserInterfaceHelpers.hideProgressBar(binding.progressBar);
    UserInterfaceHelpers.showNoContentFound(binding.errorText, requireContext(), getString(R.string.no_starred_recipes));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
