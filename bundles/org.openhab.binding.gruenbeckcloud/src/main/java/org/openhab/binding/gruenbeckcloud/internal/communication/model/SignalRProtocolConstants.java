package org.openhab.binding.gruenbeckcloud.internal.communication.model;

/**
 * GruenbeckCloud uses signalR as a hub. There are 7 message types associated with signalR: <a href=
 * "https://github.com/dotnet/aspnetcore/blob/main/src/SignalR/common/SignalR.Common/src/Protocol/HubProtocolConstants.cs">HubProtocolConstants</a>
 *
 * @author Mario Aerni - Initial contribution
 */
public interface SignalRProtocolConstants {
    int InvocationMessageType = 1;
    int StreamItemMessageType = 2;
    int CompletionMessageType = 3;
    int StreamInvocationMessageType = 4;
    int CancelInvocationMessageType = 5;
    int PingMessageType = 6;
    int CloseMessageType = 7;
}
