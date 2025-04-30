package org.javaguru.blacklist.core.services.all;

import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreResult;

public interface BlackListedGetPersonsService {

    BlackListedPersonsListCoreResult getAllBlackListedPersons(BlackListedPersonsListCoreCommand command);

}
