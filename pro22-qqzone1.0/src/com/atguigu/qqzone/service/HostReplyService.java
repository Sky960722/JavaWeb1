package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.HostReply;

public interface HostReplyService {
    HostReply getHostReplyById(Integer replyId);

    void delHostReply(Integer id);
}
