package by.afinny.infoservice.mapper;

import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.dto.ResponseBranchCoordinatesDto;
import by.afinny.infoservice.entity.BankBranch;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ResponseBankBranchMapper {

    List<ResponseBankBranchDto> toResponseBankBranch(List<BankBranch> bankBranches);

    List<ResponseBranchCoordinatesDto> toResponseBranchCoordinatesDto(List<BankBranch> bankBranches);
}
