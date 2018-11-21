package com.mojtaba_shafaei.android.lovSimple;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.mojtaba_shafaei.android.lovSimple.LovSimple.Item;

/**
 * Created by mojtaba on 9/23/17.
 */

class ListItemSingleRowHolder extends RecyclerView.ViewHolder{

private AppCompatImageView ivLogo;
private AppCompatTextView tvTitle;

static ListItemSingleRowHolder New(LayoutInflater inflater, ViewGroup parent){
  return new ListItemSingleRowHolder(inflater.inflate(R.layout.lov_simple_li_simple1, parent, false));
}

private ListItemSingleRowHolder(View itemView){
  super(itemView);
  ivLogo = itemView.findViewById(R.id.iv_logo);
  tvTitle = itemView.findViewById(R.id.tv_title);

  ViewCompat.setLayoutDirection(tvTitle, ViewCompat.LAYOUT_DIRECTION_RTL);
  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
    tvTitle.setTextDirection(View.TEXT_DIRECTION_RTL);
  }
}

void setTitle(CharSequence text){
  tvTitle.setText(text);
}

void setTypeface(Typeface typeface){
  if(typeface != null){
    tvTitle.setTypeface(typeface);
  }
}

private void setLogo(boolean showLogo, CharSequence logoUrl){
  if(showLogo){
    ivLogo.setVisibility(View.VISIBLE);

    if(StringUtils.isBlank(logoUrl)){
      ivLogo.setImageResource(R.drawable.lov_simple_ic_no_image_grey500);
    } else{
      CircularProgressDrawable progressDrawable = new CircularProgressDrawable(ivLogo.getContext());
      progressDrawable.setStyle(0);
      progressDrawable.setColorSchemeColors(0xff00ddff, 0xff99cc00, 0xffffbb33, 0xffff4444);
      progressDrawable.start();

      Glide.with(ivLogo.getContext())
          .asDrawable()
          .apply(GlideUtil.getInstance().getOptions(progressDrawable))
          .transition(withCrossFade())
          .load(logoUrl)
          .into(ivLogo);
    }
  } else{
    ivLogo.setVisibility(View.GONE);
  }
}

public void fill(boolean showLogo, Item item, String[] queries){
  if(queries != null && queries.length > 0){
    SpannableStringBuilder ssb = new SpannableStringBuilder(item.getDes());
    for(String k : queries){
      final int index = StringUtils.indexOfIgnoreCase(item.getDes(), k);
      if(index != -1){
        ssb.setSpan(
            new CustomTypefaceSpan("", LovSimple.TYPEFACE_IRANSANS_BOLD),
            index, index + k.length(),
            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
      }
    }
    setTitle(ssb);
    ssb.clear();
  } else{
    setTitle(item.getDes());
  }

  if(StringUtils.isBlank(item.getCode())){
    ivLogo.setImageResource(R.drawable.lov_simple_ic_subdirectory_arrow_left_teal_400_24dp);
  } else{
    setLogo(showLogo, item.getLogo());
  }

}
}
