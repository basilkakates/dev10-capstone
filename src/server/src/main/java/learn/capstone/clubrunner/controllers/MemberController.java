package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.Result;
import learn.capstone.clubrunner.domain.MemberService;
import learn.capstone.clubrunner.models.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {this.service = service;}

    @GetMapping
    public List<Member> findAll() {return service.findAll();}

    @GetMapping
    public Member findAdmins() {return service.findAdmins();}

    @GetMapping("/{runId}")
    public Result findById(@PathVariable int memberId) {return service.findById(memberId);}

    @GetMapping("/{userId}")
    public Result findByUserId(@PathVariable int userId) {return service.findByUserId(userId);}

    @GetMapping("/{clubId}")
    public Result findByClubId(@PathVariable int clubId) {return service.findByClubId(clubId);}

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Member member) {
        Result<Member> result = service.add(member);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Object> update(@PathVariable int memberId, @RequestBody Member member) {
        if (memberId != member.getMemberId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Member> result = service.update(member);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteById(@PathVariable int memberId) {
        if (service.deleteById(memberId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

