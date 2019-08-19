package life.majiang.community.model;

import lombok.Data;

/**
 * Question的传输对象，相对于question多了User对象，用于获取用户头像
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private String tag;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}
