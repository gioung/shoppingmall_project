## 작업계획 (테이블)

| api 목록                    | 메소드 | URL                                          | 예상일정 | 예상소요시간 | 개발일정   | 개발시간      |
| --------------------------- | ------ | -------------------------------------------- | -------- | ------------ | ---------- | ------------- |
| 회원가입                    | POST   | /api/user/signup                             | 7.17     | 1h           | 7.17       | 1h            |
| 이메일체크                  | GET    | /api/user/checkemail                         | 7.17     | 30m          | 7.17       | 30m           |
| 로그인                      | POST   | /api/user/login                              | 7.17     | 1h           | 7.17~ 7.18 | 30m           |
| 회원정보수정                | PUT    | /api/user/modification                       | 7.18     | 50m          | 7.19       | 45m           |
| 회원탈퇴                    | DELETE | /api/user/out                                | 7.18     | 50m          | 7.19       | 30m           |
| 옵션목록생성                | POST   | /api/option/list                             | 7.19     | 50m          | 7.23       | 2h(밑에 이유) |
| 관리자 상품등록             | POST   | /api/admin/product/list                      | 7.19     | 50m          | 7.23       | 2h(밑에 이유) |
| 관리자 상품목록조회         | GET    | /api/admin/product/list                      | 7.22     | 30m          | 7.24       | 20m           |
| 관리자 특정 상품조회        | GET    | /api/admin/product/list/{no}                 | 7.22     | 40m          | 7.24       | 20m           |
| 관리자 특정 상품수정        | PUT    | /api/admin/product/list/{no}                 | 7.22     | 30m          | 7.24       | 1h(밑에이유)  |
| 관리자 특정 상품 삭제       | DELETE | /api/admin/product/list/{no}                 | 7.22     | 30m          | 7.24       | 15m           |
| 관리자 특정 상품 옵션  삭제 | DELETE | /api/admin/product/list/{no}/{d_no}          | 7.23     | 30m          | 7.25       | 15m           |
| 카테고리 등록               | POST   | /api/admin/product/category                  | 7.23     | 30m          | 7.25       | 15m           |
| 카테고리 전체조회           | GET    | /api/product/category/list                   | 7.23     | 30m          | 7.25       | 15m           |
| 하위 카테고리 조회          | GET    | /api/product/category/list/{no}              | 7.23     | 30m          | 7.25       | 15m           |
| 카테고리 수정               | PUT    | /api/admin/product/category/list/{no}        | 7.24     | 30m          | 7.26       | 15m           |
| 서브 카테고리 수정          | PUT    | /api/admin/product/category/list/{no}/{s_no} | 7.24     | 30m          | 7.26       | 15m           |
| 카테고리 삭제               | DELETE | /api/admin/product/category/list/{no}        | 7.24     | 30m          | 7.26       | 15m           |
| 서브 카테고리 삭제          | DELETE | /api/admin/product/category/list/{no}/{s_no} | 7.24     | 30m          | 7.26       | 15m           |
| 장바구니 담기               | POST   | /api/cart/list                               | 7.25     | 1h           | 7.29       | 1h 20m        |
| 장바구니 목록               | GET    | /api/cart/list                               | 7.25     | 1h           | 7.29       | 30m           |
| 장바구니 수량 수정          | PUT    | /api/cart/list/{no}                          | 7.25     | 1h           | 7.29       | 30m           |
| 장바구니 아이디 수정        | PUT    | /api/cart/list                               | 7.26     | 1h           | 7.29       | 40m           |
| 장바구니 삭제               | DELETE | /api/cart/list                               | 7.26     | 1h           | 7.29       | 15m           |
| 장바구니 상품 삭제          | DELETE | /api/cart/list/{no}                          | 7.26     | 1h           | 7.29       | 15m           |
| 주문 하기                   | POST   | /api/order/list                              | 7.29     | 1h           | 7.30       | 3h( 밑에이유) |
| 주문내역조회                | GET    | /api/order/list                              | 7.29     | 30m          | 7.31       | 30m           |
| 주문내역 상세조회           | GET    | /api/order/list/{no}                         | 7.29     | 30m          | 7.31       | 30m           |
| 주문 삭제                   | DELETE | /api/order/list/{no}                         | 7.29     | 20m          | 7.31       | 30m           |



### **개발시간 지연 사유 및 문제해결 방법**

**1. 옵션목록생성** : DB설계를 이렇게 하면되지 않을까? 하고 설계를 한 후 바로 구현에 들어가다보니 문제가 생겼습니다. **문제가 생겼던 부분은 새로운 옵션을
등록하려고 할때 서로 다른 종류의 옵션이 존재한다면 그 옵션의 모든 조합을 생성해야 된다는 것**이였습니다. 예를 들어 '색상'옵션과 '사이즈'옵션이 있다고 가정을 한다면, 관리자가 '색상'을 '블랙','화이트' 그리고 '사이즈'를 '95', '100'을 만들면은 프로그램은 '블랙'95 ,'블랙'100 , '화이트'95, '화이트'100을 만들어 내야됬습니다. 문제는 이것을 '어떻게 구현할 것인가?' 라는 문제였습니다. 이 문제를 직면했을 때 떠오르는 해결방법은 크게 두 가지였습니다.<br><br>**첫번째 방법**은 **DB단에서 카티션 프로덕트를 하여서 데이터를 생성해 이를 상품상세 테이블에 WRITE하는 것이였습니다.** <br>두번째 방법으로는 옵션등록시 백엔드 app에 넘어오는 데이터들을 for문을 이용해 옵션각각을 하나의 String형태로 가공후 상품상세 테이블에 넣는 방법이였습니다.<br>맨 처음에는 첫번째 방법으로 문제를 해결하려고 시도를 했습니다. 그래서 옵션 테이블을 생성하여 각 옵션에 해당하는 번호와 값을 넣어서 이를 상품생성시 서로 곱하여 상품상세 테이블에 집어넣을라고 했습니다. 하지만 도저히 머릿속에 있는것과는 달리 '실제 DB를 어떻게? 구현은?'에 대해서 명쾌한 해결이 나오지 않았기에 이 방법을 끝내 중단하고 다른방법을 모색했습니다.<br><br>그러다 생각난 것이 두번째 방법이였습니다. **두번째 방법**은 단순했습니다. **각 옵션 데이터를 받아와 app단에서 for문을 이용하여 상품상세 테이블에 바로 insert 하는 것입니다.** 결국 시간적 여유가 없어서 이방법을 이용하여 옵션등록을 구현하였습니다. 하지만 이 방법은 사용자로부터 옵션종류에 따라서 옵션을 선택할 수 없어 UX를 떨어뜨린다는 생각이 들었습니다. 예를 들어서 쇼핑몰 사용자가 옷을 사려고 상품상세페이지에 들어가면 '사이즈'라는 선택목록이 있고 '색깔'이라는 선택목록이 있는것이 일반적이지만 이방법을 이용한다면 '옵션'이라는 선택목록만 존재하며 그 목록을 펼쳐보면 95'블랙', 95'화이트'와 같은 옵션 경우의 수를 나열해주는 불편한 UX가 나오게 됩니다. 시간적 여유가 된다면 해결해야 될 부분입니다.<br><br>

**2. 상품등록** : 상품 등록 Test시 크게 두개의 객체를 넘겼어야 했습니다. 첫번째는 상품객체고 두번째는 그 상품에 속해있는 옵션 리스트 객체를 보내야했습니다. <br>여태까지 Test시에는 하나의 객체를 보내기만 하다가 막상 두개의 객체를 보내려고 하니 아리송했습니다. 이에 대한 가장 간단한 해결책으로 떠오른것이 map객체를 생성하여 두객체를 map에 담고 map 객체 하나만을 Controller에 전송하는 것이였습니다. 하지만 Controller 단에서 받을때 문제가 생겼습니다.**전혀 예상치 못한 문제라 더욱더 시간이 걸렸습니다.** <br><br>Test단에서 객체를 Controller단으로 보낼 때는 Gson이라는 객체를 이용해서 보내는데 이객체는 보내려고하는 객체를 json형식으로 변환하여 request body에 실어서 보냅니다. **그리고 Controller단에 json데이터가 도달하면 저는 자동적으로 다시 map객체로(정확히는 HashMap) 자동변환 해줄것이라 생각했지만 LinkedTreeMap이라는 객체로 변환을 해주는 것이였습니다.** <br>저는 단순히 HashMap으로 형변환하면 해결될 줄 알았지만 이 문제는 쉽게 해결되질 않았습니다. 매개변수 단에서 HashMap 형식으로도 받아보고 아니면 Map으로 상향형변환 후 HashMap으로 변환하려고 시도를했지만 모든 시도는 물거품이 되어갔습니다. 도저히 안되겠다 싶어서 구글 검색을 계속 하던 차에 저와 비슷한 문제를 겪고있는 사람의 질문을 보고 그에 대한 답변을 보았습니다.답변은 간단했습니다 Gson객체를 이용하여서 다시 원래상태로 변환하는 코드였습니다.<br><br>**여기서 저의 개발방법에 대해 다시 돌아보게 되었습니다. 저는 성격상 제 스스로 고민하여서 해결을 하는 성격입니다. 하지만 방금과 같은 문제는 생각을 한다해도 해결될 문제가 아니며 문서를 통해 정보를 습득한 후에 해결할 수 있는 문제였습니다. 따라서, 어떤 논리상의 오류가 아니고 api자체에 대한 문제가 생기면 공식문서를 참조해서 해결해야 겠다는 깨달음을 얻게되었습니다.**<br><br>

**3. 로그인**: 기존에 사용하던 유효성 검사방법은 하나의 vo객체를 request message에 실어서 보낼때 @Valid를 이용하여 검사하는 방법이였습니다.하지만 이 방식은 객체에 달린 모든 유효성 Annotation 프로퍼티에 대해 검사를 합니다. 로그인의 경우는 오직 'ID'와 '비밀번호'프로퍼티에 대해서만 UserVo객체의 유효성을 검사해야만 했습니다. **따라서, 기존의 @Valid 방법을 쓰지 못하게 되었습니다.** <br><br>**문제를 해결하기 위해 먼저 생각을 했습니다. 그 이유는 먼저 개념적으로 어떻게 해결할지 해답을 구해놓은 후에 구현방법을 찾는것이 옳다고 생각하기 때문입니다.** 개념적으로 접근하면 기존의 방식은 VO객체에 유효성검사에 대한 Annotation이 달려있는 모든객체에 대해 유효성검사를 한다는 것입니다. 따라서, **해결방식은 '선택적'으로 유효성 검사를 하면되는것 이였습니다.** 그 후 이 개념적방법을 구현한 방법을 검색을 통해서 찾기 시작했습니다. <br><br>해결방법으로는 로그인을 위한 DTO객체를 만드는방법, Validator를 이용한 방법 두가지가 있었습니다. 제 생각으로는 스프링컨테이너에 DTO객체를 하나더 생성하여 메모리 공간을 차지하는 것 보다는 기존에 있던 VO객체를 갖다쓰는것이 메모리관점에서 좋아보여서 두번째 방식인 Validator객체를 이용해 선택적으로 프로퍼티를 검사하는 방법을 통하여 해결하였습니다.  하지만, '회원가입을 하는 유저와 로그인을 하는 유저가 너무 많아서 각각의 스레드가 이에대한 vo객체를 분리해서 사용해야 될 필요가 있지도 않을까?'라는 생각도 들어서 제가 선택한 방법이 '좋다'라고 할 수는 없다고 생각이 들었습니다. **이 기능을 구현하면서 저는 '현재상황에 따라 최선인 방법이 달라진다' 라는 점을 깨달았습니다.**<br><br>


**4. 주문 하기**: 생각해야 할 경우의 수가 많았습니다. 예를 들어, 비회원과 회원주문을 식별할 수 있는 컬럼을 뭘로해야 할까? 그 외 주문자와 받는자의 동일, 장바구니에서 주문할경우와 상품페이지에서 주문할 경우등 개인적으로 생각한 부분이 많고 그만큼 테스트 케이스를 구현해야 됬기 때문에 시간이 오래걸렸습니다.<br>
여러 기능중 가장 문제가 되었던 부분은 **비회원의 경우 주문 테이블의 ID를 어떻게 결정할 것인가?** 라는것이였고 이에 대한 해결방안으로 저는 비회원 장바구니 등록시 이용되는 TempId를 이용하자는 생각을 했습니다. 절차는 다음과 같습니다. <br>비회원의 TempId유무를 확인한 후 있으면 그 TempId를 이용하고 없으면 새롭게 TempId를 발급하여 그 Id를 이용해서 주문을 insert하고 비회원 주문내역을 조회할 수 있도록 하였습니다. TempId생성에 관한 부분은 CartService부분에 구현해 놓았습니다.

### 링크

[회원가입 및 이메일 체크](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/01.md)

[로그인](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/login.md)

[회원정보수정](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/usermodify.md)

[회원탈퇴](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/userout.md)

[옵션목록 생성 및 상품등록](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/CreateProduct.md)

[관리자 상품목록조회](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminSelectProductList.md)

[관리자 특정 상품조회](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminSelectSpecificProductList.md)

[관리자 특정 상품수정](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminUpdateProduct.md)

[관리자 특정 상품 삭제](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminDeleteProduct.md)

[관리자 상품 특정 옵션 삭제](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminDeleteProductOption.md)

[카테고리 등록](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminCreateCategory.md)

[카테고리 조회](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminSelectCategory.md)

[카테고리 수정](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminUpdateCategory.md)

[카테고리 삭제](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AdminDeleteCategory.md)

[장바구니 담기](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/AddProductToCart.md)

[장바구니 목록](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/GetProductListInCart.md)

[장바구니 수량 수정](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/updateProductQty.md)

[장바구니 아이디 수정](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/convertTempIdToUser.md)

[장바구니 삭제](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/deleteCart.md)

[장바구니 상품 삭제](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/deleteProductInCart.md)

[주문 하기](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/doOrder.md)

[주문내역조회](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/getOrderList.md)

[주문내역 상세조회](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/getOrderDetailList.md)

[주문 삭제](https://github.com/gioung/shoppingmall_project/blob/master/APIDOC/cancelOrder.md)
