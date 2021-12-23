package by.bsuir.app.service;

import by.bsuir.app.entity.ContractTransfer;
import by.bsuir.app.entity.MyDataTransfer;
import by.bsuir.app.entity.Contract;

public interface ContractService {
    Contract findContractById(Long id);
    boolean approveContract(MyDataTransfer msg);
    boolean denyContract(MyDataTransfer msg);
}
