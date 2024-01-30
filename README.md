# URL 단축 시스템

### 목차
[I. 실행 환경 구축 방법](#i-실행-환경-구축-방법)

[II. 테스트 결과](#ii-테스트-결과)

[III. 통계 시스템 디자인](#iii-통계-시스템-디자인)

### I. 실행 환경 구축 방법
프로젝트 루트에서 아래 명령을 실행해주세요.
```shell
chmod +x build_and_run.sh && ./build_and_run.sh
```
- 위 스크립트는 로컬 머신 프로세서에 따라 docker-compose 파일을 선택하여 실행 환경을 구축합니다.
- docker 기반 실행환경의 **서버 포트**는 `8080`입니다. 다만 IDE로 서버를 직접 실행하신다면 `55123`입니다.
- **DB 포트**는 `3311`입니다. **ID**와 **PW**는 각각 `root`, `password`입니다.

### II. 테스트 결과
<img width="579" alt="image" src="https://github.com/AB180-HR/240130-MJbae/assets/16694346/ccee7814-b2c8-4d38-b584-1faa5649d39b">


### III. 시스템 디자인

- **유일성을 보장하는 Short Link ID 생성**: UUID 일부 사용 또는 Hash + 62진법
  : UUID v4의 경우, 122비트(2의 112승 또는 10의 33승)으로 무작위의 랜덤값을 생성합니다. 따라서 1억(10의 9승)개의 아이디를 생성하더라도 아이디가 중복될 가능성은 매우 낫습니다. 따라서 UUID의 일부를 사용하여 유일성을 보장할 수 있습니다. 그럼에도 중복이 발생한다면 예외를 발생시키고 재시도하는 방법을 적용할 수 있습니다.
  : 입력 URL에 해시 함수를 적용하고, 해시 값을 Base62(0-9, a-z, A-Z)로 인코딩하는 방법도 고려할 수 있습니다.

- **1억번 이상의 Short Link 생성 대응**: CQRS 도입
  : 매일 1억번 이상의 Short Link를 생성하는 것은 시스템의 다른 기능에 지연 및 장애를 전파할 수 있습니다. 따라서 Short Link 생성 기능의 경우, 쓰기 전용 어플리케이션으로 분리하는 것을 고려할 수 있습니다.

- **매일 10억번 이상의 Short Link 클릭 대응**: 캐싱 솔루션 도입
  : 동일한 Short Link Id로 요청하는 경우, 캐싱 솔루션을 거치도록 합니다. 캐싱 솔루션으로부터 원본 URL을 가져온다면 DB 지연이 발생하지 않기 때문에 빠르게 응답할 수 있습니다.

- **Short Link 마다 최근 7일 click 수 제공 방법**: 이벤트 기반 클릭 수 집계
  : 매일 10억 건의 클릭 시 이벤트를 발생시키고, 컨슈머 어플리케이션에서 클릭 수를 관리하는 방법을 고려할 수 있습니다. 본 기능의 경우, 높은 처리량이 요구되므로 메시지 브로커로 카프카를 채택하고 메시지는 'at least once' 방식으로 전달하는 것을 고려할 수 있습니다. 컨슈머 어플리케이션에서 이벤트를 수신하여 클릭수를 저장하고 최근 7일 이내의 클릭수를 집계하여 클라이언트에게 제공할 수 있습니다. 