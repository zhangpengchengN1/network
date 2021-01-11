package com.zpc.myapplication.network.callback;


public interface LifeCycleModel {
	void onCreateView();

	void onResume();

	void onPause();

	void onDestroyView();
}
