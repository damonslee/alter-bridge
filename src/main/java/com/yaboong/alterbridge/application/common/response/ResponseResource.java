package com.yaboong.alterbridge.application.common.response;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Created by yaboong on 2019-09-13
 *
 * 이 클래스 만든 이유
 *  ResponseBase 에 content 로 리소스 반환하면,
 *  ResponseBase 없이 body 에 Resource 타입을 달때와 link 정보가 다르게 표시됨
 *
 *  ResponseBase 로 wrapping 안하는 경우
 *    "_links": {
 *     "self": {
 *       "href": "http://localhost:18080/posts/1"
 *     },
 *     "profile": {
 *       "href": "http://localhost:18080/posts"
 *     }
 *   }
 *
 *  ResponseBase 로 wrapping 하는 경우
 *
 *  "links": [
 *    {
 *      "rel": "self",
 *      "href": "http://localhost:18080/posts/1",
 *      "hreflang": null,
 *      "media": null,
 *      "title": null,
 *      "type": null,
 *      "deprecation": null
 *    },
 *    {
 *      "rel": "profile",
 *      "href": "http://localhost:18080/posts",
 *      "hreflang": null,
 *      "media": null,
 *      "title": null,
 *      "type": null,
 *      "deprecation": null
 *    }
 *  ]
 *
 *  wrapping 안하는 경우의 _links 형태가 클라이언트에서 사용하기 좋아보임
 *
 *  ResponseBase 로 Wrapping 하는 경우에도 _links 형태의 응답을 줄 수 있음
 *  그렇게 하기 위해서 ResponseResource 클래스로 다시 ResponseBase 를 wrapping 함
 *
 * 사용예제
 *  Post newPost = postService.create(postDto);
 *  Link link1 = linkTo(PostController.class).withRel("profile");
 *  PostResource postResource = new PostResource(newPost);
 *  postResource.add(link1);
 *  return ResponseEntity
 *          .created(linkTo(PostController.class).slash(newPost.getPostId()).toUri())
 *          .body(new ResponseResource<>(ResponseBase.of(ApiResponse.OK, postResource)));
 *
 * 근데 ResponseBase 로 wrapping 해도 결국 REST 하게 만들려면
 * ResponseBase 의 각 필드가 어떤 의미를 가지는지 profile 링크를 달아줘야 해서 결국 문서에 대한 링크를 걸어야함.
 * 그럴바에는 그냥 ResponseBase 로 wrapping 하지 않고,
 * HTTP 상태코드로 응답주고, 에러인 경우에도 profile 링크에 error 케이스에 대한 문서 링크 걸도록 변경할 예정
 * 그래서 요 클래스는 deprecated 로 변경
 */
@Deprecated(since = "HATEAOS, Rest Docs applied")
public class ResponseResource<T> extends Resource<ResponseBase<T>> {
    public ResponseResource(ResponseBase<T> content, Link... links) {
        super(content, links);
    }
}
