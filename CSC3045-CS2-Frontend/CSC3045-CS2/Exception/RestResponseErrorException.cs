using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Exception
{

    class RestResponseErrorException : System.Exception
    {
        public HttpStatusCode StatusCode { get; set; }

        public RestResponseErrorException()
        {

        }

        public RestResponseErrorException(string message)
        : base(message)
        {

        }

        public RestResponseErrorException(string message, HttpStatusCode statusCode)
        : base(message)
        {
            StatusCode = statusCode;
        }

        public RestResponseErrorException(string message, HttpStatusCode statusCode, System.Exception inner)
        : base(message, inner)
        {
            StatusCode = statusCode;
        }
    }
}
