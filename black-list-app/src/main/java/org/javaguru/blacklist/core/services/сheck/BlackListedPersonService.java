package org.javaguru.blacklist.core.services.сheck;

import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreResult;

public interface BlackListedPersonService {

    BlackListedPersonCoreResult check(BlackListedPersonCoreCommand command);

}
