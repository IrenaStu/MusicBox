package com.example.musicBox.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SongParam {

@NotBlank
 private  String songName;

 private String url;
}
