package by.bsuir.app.util.connection;

import by.bsuir.app.dao.*;
import by.bsuir.app.entity.MyDataTransfer;
import by.bsuir.app.dao.impl.*;
import by.bsuir.app.entity.*;
import by.bsuir.app.service.ContractService;
import by.bsuir.app.service.CreditService;
import by.bsuir.app.service.impl.ContractServiceImpl;
import by.bsuir.app.service.impl.CreditServiceImpl;
import by.bsuir.app.service.snapshot.CurrencyService;
import by.bsuir.app.service.snapshot.Manipulator;
import by.bsuir.app.util.Services;
import by.bsuir.app.util.constants.Status;
import lombok.extern.log4j.Log4j2;

import javax.mail.MessagingException;

import static by.bsuir.app.util.constants.ConstantsMSG.INCORRECT_VALUE_MSG;

@Log4j2
public class CommandHandler {
    private static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    private static final HistoryLogDao ENTRANCE_HISTORY_DAO = new HistoryLogDaoImpl();
    private static final Manipulator manipulator = new Manipulator();
    private static final PassportDao personalData = new PassportDaoImpl();
    private static final CreditInfoDao creditInfoDao = new CreditDaoImpl();
    private static final ContractDao contractDao = new ContractDaoImpl();
    private static final CurrencyDao currencyDao = new CurrencyDaoImpl();
    private static final ContractService contractService = new ContractServiceImpl();
    private static final CreditService creditService = new CreditServiceImpl();
    private static final CurrencyService currencyService = new CurrencyService();

    public static Object execute(Commands command, Object obj) {
        Object response = null;

        //TODO COMMAND
        try {
            response = switch (command) {
                case USER_ADD_OR_UPDATE -> ACCOUNT_DAO.saveOrUpdate((Account) obj);
                case AUTHORISATION -> ACCOUNT_DAO.auth((Account) obj);
                case DELETE_USER -> ACCOUNT_DAO.delete((Account) obj);
                case DELETE_USER_BY_ID -> ACCOUNT_DAO.deleteById((Long) obj);
                case GET_USER_BY_ID -> ACCOUNT_DAO.findById((Long) obj);
                case GET_ALL_USERS -> ACCOUNT_DAO.findAll();
                case GET_USER_BY_LOGIN -> ACCOUNT_DAO.findByLogin((String) obj);
                case PASSWORD_RECOVERY -> ACCOUNT_DAO.resetPassword((Account) obj);
                case REGISTRATION -> ACCOUNT_DAO.registration((Account) obj);
                case GET_LAUNCHES_COUNT_DATA -> ENTRANCE_HISTORY_DAO.findAllGropedByDate();
                case GET_ALL_USER_LAUNCHES -> ENTRANCE_HISTORY_DAO.findAllUserLaunches();
                case SEND_MESSAGE_TO_USER -> Services.sendMessage((Message) obj);
                case SAVE_CREDIT_DATA_LOCAL_STORAGE -> manipulator.saveObjectInMemory();
                case RESTORE_CREDIT_DATA_LOCAL_STORAGE -> manipulator.getObjectFromMemory();
                case GET_AGE_PERCENT_PROPORTION -> personalData.findAgePercentProportion();
                case GET_ALL_CREDITS_INFO -> creditInfoDao.findAll();
                case GET_CREDIT_BY_ID -> creditService.findCreditById((Long) obj);
                case ADD_NEW_CONTRACT -> contractDao.save((Contract) obj);
                case GET_ALL_CREDITS_GROUPED_BY_NAME -> creditInfoDao.findAllGroupedByCreditName();
                case GET_ALL_CURRENCIES -> currencyDao.findAll();
                case GET_CONTRACTS_BY_USER_ID -> contractDao.findAllByUserId((Long) obj);
                case GET_CONTRACT_BY_ID -> contractService.findContractById((Long) obj);
                case REMOVE_CONTRACT -> contractDao.delete((Contract) obj);
                case GET_ALL_CONTRACTS -> contractDao.findAll();
                case APPROVE_CONTRACT_BY_ID -> contractService.approveContract((MyDataTransfer) obj);
                case DENY_CONTRACT_BY_ID -> contractService.denyContract((MyDataTransfer) obj);
                case DELETE_CREDIT_BY_ID -> creditInfoDao.deleteById((Long) obj);
                case ADD_OR_UPDATE_CREDIT -> creditInfoDao.saveOrUpdate((CreditInfo) obj);
                case ADD_NEW_CREDIT -> creditInfoDao.save((CreditInfo) obj);
                case GET_COUNT_OF_USER_CREDITS_BY_LOGIN -> contractDao.findUserContractsCountByLogin((String) obj);
                case GET_CURRENCY_BY_ID -> currencyService.findCurrecyById((Long) obj);
                default -> defaultBranch(command);

            };
        } catch (ClassCastException | MessagingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    private static Object defaultBranch(Commands command) {
        log.info(INCORRECT_VALUE_MSG + command);
        return Status.REQUEST_ERROR;
    }
}
