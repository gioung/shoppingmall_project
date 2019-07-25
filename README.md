## 작업계획 (테이블)

| api 목록                    | 메소드 | URL                                 | 예상일정 | 예상소요시간 | 개발일정   | 개발시간      |
| --------------------------- | ------ | ----------------------------------- | -------- | ------------ | ---------- | ------------- |
| 회원가입                    | POST   | /api/user/signup                    | 7.17     | 1h           | 7.17       | 1h            |
| 이메일체크                  | GET    | /api/user/checkemail                | 7.17     | 30분         | 7.17       | 30분          |
| 로그인                      | POST   | /api/user/login                     | 7.17     | 1h           | 7.17~ 7.18 | 30분          |
| 회원정보수정                | PUT    | /api/user/modification              | 7.18     | 50분         | 7.19       | 45분          |
| 회원탈퇴                    | DELETE | /api/user/out                       | 7.18     | 50분         | 7.19       | 30분          |
| 옵션목록생성                | POST   | /api/option/list                    | 7.19     | 50분         | 7.23       | 2h(밑에 이유) |
| 관리자 상품등록             | POST   | /api/admin/product/list             | 7.19     | 50분         | 7.23       | 2h(밑에 이유) |
| 관리자 상품목록조회         | GET    | /api/admin/product/list             | 7.20     | 30분         | 7.24       | 20분          |
| 관리자 특정 상품조회        | GET    | /api/admin/product/list/{no}        | 7.20     | 40분         | 7.24       | 20분          |
| 관리자 특정 상품수정        | PUT    | /api/admin/product/list/{no}        | 7.20     | 30분         | 7.24       | 1h(밑에이유)  |
| 관리자 특정 상품 삭제       | DELETE | /api/admin/product/list/{no}        | 7.20     | 30분         | 7.24       | 15분          |
| 관리자 특정 상품 옵션  삭제 | DELETE | /api/admin/product/list/{no}/{d_no} | 7.21     | 30분         | 7.25       | 15분          |
| 카테고리 등록               | POST   | /api/admin/product/category         | 7.21     | 30분         | 7.25       | 15분          |
| 카테고리 전체조회           | GET    | /api/product/category/list          | 7.21     | 30분         | 7.25       | 15분          |
| 하위 카테고리 조회          | GET    | /api/product/category/list/{no}     | 7.21     | 30분         | 7.25       | 15분          |



**개발시간 지연 사유**

 	1. 옵션목록생성 : DB설계를 이렇게 하면되지 않을까? 하고 설계를 한 후 바로 구현에 들어가다보니 문제가 생겼습니다.지금의 DB구조로는 안될것 같아서 다시 DB를 수정후 구현, 이를 반복하였습니다.  이런 과정을 반복하다보니 DB설계를 신중히 해야된다는 것을 느꼈고 구현을 할때도 가장 간단한 모델이 무엇인지 고민하는 습관을 길렀습니다.

2. 상품등록 : 이론상으로는 문제가 없었지만 구현을 함에 있어서 사소한 부분 (mybatis 문법오류) 에서 애를먹어서 늦어지게 되었습니다. 처음에는 각종 blog를 참고하다가 틀린정보가 많아 공식문서를 참조하여 해결하였습니다.
3. 관리자 특정 상품수정: 기존의 사용하던 유효성 검사방법은 하나의 vo객체를 request message에 실어서 보낼때 @Valid를 이용하여 검사하는 방법이였습니다. 하지만, 두 개 이상의 vo객체를 request message에 담아보낼때는 Map을 이용하는 방법으로 보냈습니다. 따라서, 기존의 @Valid 방법이 먹히지 않아 새로운 유효성 검사 방법인 Validator객체를 이용하는 방법을 찾아 적용하는데 시간이 걸렸습니다.

### 링크

[회원가입 및 이메일 체크](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/01.md)

[로그인](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/login.md)

[회원정보수정](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/usermodify.md)

[회원탈퇴](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/userout.md)

[옵션목록 생성 및 상품등록](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/CreateProduct.md)

[관리자 상품목록조회](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminSelectProductList.md)

[관리자 특정 상품조회](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminSelectSpecificProductList.md)

[관리자 특정 상품수정](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminUpdateProduct.md)

[관리자 특정 상품 삭제]()

[관리자 상품 특정 옵션 삭제](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminDeleteProductOption.md)

[카테고리 등록](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminCreateCategory.md)

[카테고리 전체조회]()

[하위 카테고리 조회]()