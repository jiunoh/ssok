package com.example.jiun.sookpam.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jiun.sookpam.message.ContentActivity;
import com.example.jiun.sookpam.message.ContentItem;
import com.example.jiun.sookpam.model.vo.RecordVO;

public class MsgContentGenerator {
    public static void showMessageBody(Context context, RecordVO data) {
        Intent intent = new Intent(context, ContentActivity.class);
        ContentItem contentItem  = new ContentItem();
        contentItem.setCategory(data.getCategory());
        contentItem.setDivision(data.getDivision());
        contentItem.setBody(data.getMessage().getBody());
        contentItem.setPhone(data.getMessage().getPhoneNumber());
        contentItem.setDate(data.getMessage().getDate());
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJECT", contentItem);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
