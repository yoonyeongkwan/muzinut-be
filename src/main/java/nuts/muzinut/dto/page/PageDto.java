package nuts.muzinut.dto.page;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class PageDto<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PageDto(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.pageNumber = pageNumber+1;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
