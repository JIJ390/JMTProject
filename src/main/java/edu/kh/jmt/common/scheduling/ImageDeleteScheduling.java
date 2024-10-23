package edu.kh.jmt.common.scheduling;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.kh.jmt.common.scheduling.service.SchedulingService;
import lombok.extern.slf4j.Slf4j;

@Component // bean 등록
@Slf4j
@PropertySource("classpath:/config.properties")
public class ImageDeleteScheduling {

	@Autowired
	private SchedulingService service;
	
	@Value("${my.profile.folder-path}")
	private String profilePath; // 회원 이미지 서버 저장 경로
	
	@Value("${my.board.folder-path}")
	private String boardPath; // 게시글 이미지 서버 저장 경로
	
	@Scheduled(cron = "0 0 * * * *") // 0초 시작 20초마다 수행 
	public void imageDelete() {
		
		// 1. DB에 저장되어있는 파일명 모두 조회
		// - MEMBER.PROFILE_IMG 에서 파일명만 조회
		// - BOARD.FILE_RENAME만 조회
		// - 두 결과를 UNION해서 하나의 SELECT 결과로 반환 받기
		
		List<String> dbFileNameList = service.getDbfileNameList();
		
		
		if( dbFileNameList.isEmpty() ) {
			
		}
		
		// 2. 서버에 저장된 이미지목록 모두 조회
		File profileFolder = new File(profilePath);
		File boardFolder = new File(boardPath);
		
		// 폴더에 저장된 파일 목록을 File[] 형태로 반환 받아 List<File>로 변환
		List<File> profileList = Arrays.asList(profileFolder.listFiles()); 
		
		
		List<File> boardList = Arrays.asList(boardFolder.listFiles());
		
		// 두 List를 하나로 합치기
		List<File> serverList = new ArrayList<>();
		serverList.addAll(profileList);
		serverList.addAll(boardList);
		
		
		// 3. dbFileNameList와 serverList의 파일명 비교
		// -> serverList에는 존재하는데
		//    dbFileNameList에 없으면
		// 		서버에 저장된 이미지 삭제
		for(File serverFile : serverList) {
			
			// 서버 파일명이 BD 파일 목록에 없을 경우
			if( !dbFileNameList.contains(serverFile.getName() ) ) { 
				
				log.info("{} 삭제	", serverFile.getName());
				serverFile.delete(); // 파일 삭제
				
			}
			
		}
		
		log.info("------------------- 이미지 삭제 스케쥴러 종료 ------------------------");
		
		
	}
	
	
}
