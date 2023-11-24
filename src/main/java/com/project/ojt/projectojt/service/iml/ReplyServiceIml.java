package com.project.ojt.projectojt.service.iml;

import com.project.ojt.projectojt.entity.Reply;
import com.project.ojt.projectojt.repository.ReplyRepository;
import com.project.ojt.projectojt.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceIml implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public Reply addReply(Reply replyObject) {
        return replyRepository.save(replyObject);
    }
}
