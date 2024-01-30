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


### III. 통계 시스템 디자인
