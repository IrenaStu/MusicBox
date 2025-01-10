package com.example.musicBox.service;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.PlayListDto;
import com.example.musicBox.model.entity.PlayListEntity;
import com.example.musicBox.model.enums.PlayListStatus;
import com.example.musicBox.model.mapper.PlayListMapper;
import com.example.musicBox.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListService {

    @Autowired
    private  final PlayListRepository playListRepository;



    public Page<PlayListDto> viewAllPlayLists(Integer page, Integer size){
        return playListRepository.findAll(PlayListStatus.DELETE, PageRequest.of(page,size)).map(PlayListDto::new);
    }


    public PlayListDto viewPlayListById(Long playListId){
        PlayListEntity playList = playListRepository.findById(playListId)
                .orElseThrow(()-> new CustomException(ErrorMessage.PLAY_LIST_NOT_FOUND));
        return PlayListMapper.toPlayListDto(playList);
    }



}
