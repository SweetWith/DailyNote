package com.sweetwith.dailynote.service.reply;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.reply.Reply;
import com.sweetwith.dailynote.domain.reply.ReplyRepository;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.web.dto.ReplyRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {
    private ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    // Create
    public Long registerReply(ReplyRequestDto replyRequestDto) {
        Reply reply = new Reply(replyRequestDto);
        return replyRepository.save(reply).getId();
    }

    // Read
    public List<Reply> getReplyByPost(Post post) {
        List<Reply> replys = replyRepository.findByPost(post);

        List<Reply> parents = new ArrayList<>();
        List<Reply> childs = new ArrayList<>();
        List<Reply> ret = new ArrayList<>();

        // Order by CreatedData as ASC
        replys.sort(Comparator
                .comparing((Reply reply) -> reply.getCreatedDate().toLocalDate())
                .thenComparing(Comparator
                        .comparing((Reply reply) -> reply.getCreatedDate().toLocalTime())
                        ));

        // Separate parent and child reply
        for (Reply reply : replys) {
            if (reply.getReplyParentId() == 0)
                parents.add(reply);
            else
                childs.add(reply);
        }

        // Order by parent reply Id
        for (Reply parent : parents) {
            ret.add(parent);
            for (Reply child : childs) {
                if(parent.getId().equals(child.getReplyParentId()))
                    ret.add(child);
            }
        }
        return ret;
    }

    // Update
    public int updateContent(Long replyId, String content, User user){
        if(!hasUpdateAuthority(replyId,user))
            return 0;
        return replyRepository.updateContent(replyId, content);
    }

    // delete
    public int deleteReply(Long replyId, User user){
        if(!hasUpdateAuthority(replyId,user))
            return 0;
        return replyRepository.deleteReply(replyId);
    }

    private boolean hasUpdateAuthority(Long replyId, User user){
        Reply reply = replyRepository.findById(replyId).get();
        if(reply.getUser().getId().equals(user.getId()))
            return true;
        return false;
    }

}
