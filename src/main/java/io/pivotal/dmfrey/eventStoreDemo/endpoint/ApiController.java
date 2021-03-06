package io.pivotal.dmfrey.eventStoreDemo.endpoint;

import feign.FeignException;
import io.pivotal.dmfrey.eventStoreDemo.domain.events.DomainEvent;
import io.pivotal.dmfrey.eventStoreDemo.domain.model.Board;
import io.pivotal.dmfrey.eventStoreDemo.domain.service.BoardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/boards" )
public class ApiController {

	private static final Logger log = LoggerFactory.getLogger( ApiController.class );
	
    private final BoardService service;

    public ApiController( final BoardService service ) {

        this.service = service;

    }

    @PostMapping
    public ResponseEntity createBoard() {
        log.info( "createBoard : enter" );

        return this.service.createBoard();
    }

    @GetMapping( path = "/{boardUuid}" )
    public ResponseEntity<Board> board( @PathVariable( "boardUuid" ) UUID boardUuid ) {
        log.info( "board : enter" );

        ResponseEntity<Board> responseEntity = this.service.board( boardUuid );
        log.info( "board : responseEntity=" + responseEntity );

        log.info( "board : exit" );
        return responseEntity;
    }

    @GetMapping( path = "/{boardUuid}/history" )
    public ResponseEntity<List<DomainEvent>> history( @PathVariable( "boardUuid" ) UUID boardUuid ) {
        log.info( "history : enter" );

        try {

            ResponseEntity<List<DomainEvent>> responseEntity = this.service.history( boardUuid );
            log.info( "history : responseEntity=" + responseEntity );

            log.info( "history : exit" );
            return responseEntity;

        } catch( FeignException e ) {

            return ResponseEntity
                    .ok( new ArrayList<>() );

        }

    }

    @PatchMapping( "/{boardUuid}" )
    public ResponseEntity renameBoard( @PathVariable( "boardUuid" ) UUID boardUuid, @RequestParam( "name" ) String name, final UriComponentsBuilder uriComponentsBuilder ) {
        log.info( "renameBoard : enter" );

        return this.service.renameBoard( boardUuid, name );
    }

    @PostMapping( "/{boardUuid}/stories" )
    public ResponseEntity addStoryToBoard( @PathVariable( "boardUuid" ) UUID boardUuid, @RequestParam( "name" ) String name, final UriComponentsBuilder uriComponentsBuilder ) {
        log.info( "addStoryToBoardBoard : enter" );

        return this.service.addStory( boardUuid, name );
    }

    @PutMapping( "/{boardUuid}/stories/{storyUuid}" )
    public ResponseEntity updateStoryOnBoard( @PathVariable( "boardUuid" ) UUID boardUuid, @PathVariable( "storyUuid" ) UUID storyUuid, @RequestParam( "name" ) String name ) {
        log.info( "updateStoryOnBoard : enter" );

        return this.service.updateStory( boardUuid, storyUuid, name );
    }

    @DeleteMapping( "/{boardUuid}/stories/{storyUuid}" )
    public ResponseEntity removeStoryFromBoard( @PathVariable( "boardUuid" ) UUID boardUuid, @PathVariable( "storyUuid" ) UUID storyUuid ) {
        log.info( "removeStoryFromBoard : enter" );

        return this.service.deleteStory( boardUuid, storyUuid );
    }

}
