/**
 * Set a line of text and a URL to open in the browser when clicked
 */
package org.wordpress.android.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.wordpress.android.R;
import org.wordpress.android.models.Note;
import org.wordpress.android.ui.reader.ReaderActivityLauncher;

public class DetailHeader extends LinearLayout {
    private Note mNote;
    private String mUrl;

    public DetailHeader(Context context){
        super(context);
    }
    public DetailHeader(Context context, AttributeSet attributes){
        super(context, attributes);
    }
    public DetailHeader(Context context, AttributeSet attributes, int defStyle){
        super(context, attributes, defStyle);
    }
    public TextView getTextView(){
        return (TextView) findViewById(R.id.label);
    }
    public void setText(CharSequence text){
        getTextView().setText(text);
    }
    public void setUrl(final String url){
        setClickable(true);
        mUrl = url;
    }

    public void setClickable(boolean clickable){
        super.setClickable(clickable);
        View indicator = findViewById(R.id.indicator);
        if (clickable == false) {
            indicator.setVisibility(GONE);
        } else {
            indicator.setVisibility(VISIBLE);
            setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Context context = getContext();
                    // if this is a note about a post, show it in reader post detail
                    boolean isComment = (mNote != null && mNote.blogId != 0 && mNote.postId != 0 && mNote.commentId != 0);
                    boolean isPost = (mNote != null && mNote.blogId != 0 && mNote.postId != 0 && mNote.commentId == 0);
                    boolean isBlog = (mNote != null && mNote.blogId != 0 && mNote.postId == 0 && mNote.commentId == 0);
                    if (isPost || isComment) {
                        ReaderActivityLauncher.showReaderPostDetail(context, mNote.blogId, mNote.postId);
                    } else if (!TextUtils.isEmpty(mUrl)) {
                        Intent intent = new Intent(context, NotificationsWebViewActivity.class);
                        intent.putExtra(NotificationsWebViewActivity.URL_TO_LOAD, mUrl);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public void setNote(Note note) {
        mNote = note;
    }
}