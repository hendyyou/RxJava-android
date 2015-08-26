package com.strv.rxjavademo.adapter;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strv.rxjavademo.R;
import com.strv.rxjavademo.model.GithubUserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by adamcerny on 26/08/15.
 */
public class CardsGithubAdapter extends RecyclerView.Adapter<CardsGithubAdapter.ViewHolder>
{
	List<GithubUserModel> mGithubItems;

	public CardsGithubAdapter()
	{
		super();
		mGithubItems = new ArrayList<GithubUserModel>();
	}


	public void addGithubItem(GithubUserModel githubItem)
	{
		mGithubItems.add(githubItem);
		notifyDataSetChanged();
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.card_github_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(v);
		return viewHolder;
	}


	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		GithubUserModel model = mGithubItems.get(position);

		Picasso.with(holder.image.getContext())
				.load(model.getAvatarUrl())
				.into(holder.image);

		holder.name.setText("Name: " + model.getLogin());
		holder.repos.setText("Public repos # : " + model.getPublicRepos());
	}


	@Override
	public int getItemCount()
	{
		return mGithubItems.size();
	}


	class ViewHolder extends RecyclerView.ViewHolder
	{
		@Bind(R.id.card_github_imageview)
		ImageView image;

		@Bind(R.id.card_github_name_textview)
		TextView name;

		@Bind(R.id.card_github_repos_textview)
		TextView repos;

		public ViewHolder(View viewToRecyclate)
		{
			super(viewToRecyclate);
			ButterKnife.bind(this, viewToRecyclate);
		}
	}
}
