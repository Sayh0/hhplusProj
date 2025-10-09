package kr.hhplus.be.server.wallet.mapper;

import kr.hhplus.be.server.wallet.vo.WalletVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 잔고 데이터 접근 매퍼
 */
@Mapper
public interface WalletMapper {
    
    /**
     * 고객 잔고 조회
     */
    WalletVo findWalletByCustomerId(@Param("customerId") Long customerId);
    
    /**
     * 잔고 생성 (첫 충전 시)
     */
    void insertWallet(@Param("customerId") Long customerId, @Param("balance") Long balance, 
                      @Param("firstInputId") String firstInputId);
    
    /**
     * 잔고 업데이트
     */
    void updateWalletBalance(@Param("customerId") Long customerId, @Param("balance") Long balance, 
                             @Param("lastInputId") String lastInputId);
    
}
