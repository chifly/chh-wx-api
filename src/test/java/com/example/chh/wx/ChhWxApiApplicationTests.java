package com.example.chh.wx;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.chh.wx.db.pojo.MessageEntity;
import com.example.chh.wx.db.pojo.MessageRefEntity;
import com.example.chh.wx.db.pojo.TbMeeting;
import com.example.chh.wx.service.MeetingService;
import com.example.chh.wx.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ChhWxApiApplicationTests {
    @Autowired
    private MessageService messageService;

    @Autowired
    private MeetingService meetingService;
    @Test
    void contextLoads() {
        for (int i = 1; i <= 100; i++) {
            MessageEntity message = new MessageEntity();
            message.setUuid(IdUtil.simpleUUID());
            message.setSenderId(0);
            message.setSenderName("系统消息");
            message.setMsg("这是第" + i + "条测试消息");
            message.setSendTime(new Date());
            String id=messageService.insertMessage(message);

            MessageRefEntity ref=new MessageRefEntity();
            ref.setMessageId(id);
            ref.setReceiverId(17); //接收人ID
            ref.setLastFlag(true);
            ref.setReadFlag(false);
            messageService.insertRef(ref);
        }
    }

    @Test
    void createMeetingData(){
        for (int i = 0; i < 20; i++) {
            TbMeeting meeting = new TbMeeting();
            meeting.setId((long)i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议" + i);
            meeting.setCreatorId(17L);
            meeting.setDate(DateUtil.today());
            meeting.setPlace("线上会议室");
            meeting.setStart("8:00");
            meeting.setEnd("10:00");
            meeting.setType((short)1);
            meeting.setMembers("[17,18]");
            meeting.setDesc("会议研讨测试");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus((short)3);
            meetingService.insertMeeting(meeting);
        }
    }
}
