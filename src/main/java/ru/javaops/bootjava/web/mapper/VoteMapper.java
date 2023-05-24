package ru.javaops.bootjava.web.mapper;

import org.springframework.stereotype.Component;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.web.dto.VoteDto;

@Component
public class VoteMapper implements Mapper<Vote, VoteDto> {

    private final RestaurantMapper restaurantMapper;

    public VoteMapper(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Vote toEntity(VoteDto dto) {
        Vote vote = new Vote();
        vote.setId(dto.getId());
        vote.setVoteDate(dto.getVoteDate());
        vote.setVoteTime(dto.getVoteTime());
        vote.setRestaurant(restaurantMapper.toEntity(dto.getRestaurant()));
        return vote;
    }

    @Override
    public VoteDto toDto(Vote entity) {
        return new VoteDto(
                entity.getId(),
                restaurantMapper.toDto(entity.getRestaurant()),
                entity.getVoteDate(),
                entity.getVoteTime()
        );
    }
}
