## e-커머스 플랫폼 프로젝트입니다.

## Getting Started

### Prerequisites

#### Running Docker Containers

`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```

### API Documentation

API 명세서는 [api-spec](./docs/api-spec.md)에서 확인할 수 있습니다.

### Database Schema

데이터베이스 스키마는 [ERD](./docs/erd.md)에서 확인할 수 있습니다.
ERD PDF 파일은 [ERD PDF](./docs/erd.drawio.pdf)에서 다운로드할 수 있습니다.

### Infrastructure

프로젝트 인프라 설계는 [Infrastructure](./docs/infrastructure.md) 문서에서 확인할 수 있습니다.
- AWS VPC 기반 아키텍처
- Spring Boot 애플리케이션 (EC2)
- MySQL 데이터베이스 (RDS)
- Redis 캐시 (ElastiCache)
- S3 파일 저장소
- API Gateway 및 Load Balancer