package org.javaguru.blacklist.core.services.delete;

import org.javaguru.blacklist.core.api.command.BlackListedPersonDeleteCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonDeleteCoreResult;

public interface BlackListedPersonDeleteCoreService {
    BlackListedPersonDeleteCoreResult delete(BlackListedPersonDeleteCoreCommand command);
}